package com.lemonbily.springboot.configurer.Intercept;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import com.lemonbily.springboot.util.TokenUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 请求处理前，检查是否是
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;
        String token = request.getHeader("token");
        String userId = request.getHeader("id");

        logger.info("------------AuthenticationInterceptor-------------");
        logger.info("token ：" + token);
        logger.info("userId ：" + userId);
        if (null == token || null == userId) {
            JSONObject object = JsonUtil.generateJsonResponse(
                    ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE_CONTENT,null);
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().println(object);
        }else{

            if (TokenUtil.isTokenEffective(userId, token)) {
                result = true;
            }else {
                JSONObject object = JsonUtil.generateJsonResponse(
                        ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE,
                        ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE_CONTENT,null);
                response.setContentType("text/json;charset=UTF-8");
                response.getWriter().println(object);

                //token不匹配说明过期了
                TokenUtil.removeToken(userId);
            }
        }
        return result;
    }

    /**
     * 请求处理后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求处理完成
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
