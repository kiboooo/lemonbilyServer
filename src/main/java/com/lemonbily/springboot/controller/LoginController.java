package com.lemonbily.springboot.controller;


import com.lemonbily.springboot.bean.CommonBean;
import com.lemonbily.springboot.bean.Token;
import com.lemonbily.springboot.entity.Login;
import com.lemonbily.springboot.mapper.LoginMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import com.lemonbily.springboot.util.TokenUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/LoginController")
public class LoginController extends BaseController<Login> {

    @Autowired(required = false)
    LoginMapper loginMapper;
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @RequestMapping(value = "/registered",
            params = "com.lemonbily.springboot.entity.Login",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String insert(@RequestBody Login record) {
        return null;
    }

    @Override
    @RequestMapping(value = "/logout/{id}", produces = "application/json;charset=UTF-8")
    public String deleteByID(@PathVariable("id") int id) {
        return null;
    }

    @Override
    @RequestMapping(value = "/changePassWord" ,params = "", produces = "application/json;charset=UTF-8")
    public String updete(Login record) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getLoginAll", produces = "application/json;charset=UTF-8")
    public String selectAll() {
        List<Login> list= loginMapper.selectAll();
        if ((list) != null) {
            TokenUtil.generateTokenAddMap(list.get(0));
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE, list)
                    .toJSONString();
        } else {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT)
                    .toJSONString();
        }
    }

    @Override
    @RequestMapping(value = "/getLogin/{id}", produces = "application/json;charset=UTF-8")
    public String selectByID(@PathVariable("id") int id) {
        logger.info("/getLogin/{id}");
        Login login = loginMapper.selectByID(id);
        if (null == login) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT )
                    .toJSONString();
        }else{
            Token token = TokenUtil.generateLoginUserToken(login);
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,token.getToken(), login)
                    .toJSONString();
        }

    }

    @RequestMapping(value = "/checkLiveTime/{id}",produces = "application/json;charset=UTF-8")
    public String checkLiveTime(@PathVariable("id") int id) {
        if (id < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE, "请求账号错误")
                    .toJSONString();
        }
        Date lastDate = loginMapper.liveTimeCheck(id);
        if (lastDate == null
                || ((System.currentTimeMillis() - lastDate.getTime()) >= CommonBean.liveTimeLimit))
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE,
                            ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE_CONTENT)
                    .toJSONString();
        else {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_LIFE_CODE,
                            ResponseCodeUtil.LEMONBILY_LOGIN_LIFE_CODE_CONTENT)
                    .toJSONString();
        }
    }
}
