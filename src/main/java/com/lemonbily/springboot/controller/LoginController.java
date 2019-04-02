package com.lemonbily.springboot.controller;


import com.lemonbily.springboot.bean.CommonBean;
import com.lemonbily.springboot.bean.Token;
import com.lemonbily.springboot.entity.Login;
import com.lemonbily.springboot.mapper.LoginMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import com.lemonbily.springboot.util.TokenUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass()); //日志对象


    /**
     * 注册接口
     * url：/LoginController/registered
     * method : post
     * 参数：login对象的json数据
     *
     * @param record
     * @return
     */
    @Override
    @RequestMapping(value = "/registered",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String insert(@RequestBody Login record) {

        /*
         *  校验传入对象开始
         */
        if (null == record) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_NULL, null)
                    .toJSONString();
        }
        if (null == record.getId() || record.getId() < 1000) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_ID_NULL, null)
                    .toJSONString();
        }
        if (null == record.getName() || record.getName().equals("")) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_NAME_NULL, null)
                    .toJSONString();
        }
        if (null == record.getLpassword() || record.getLpassword().equals("")) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PASS_NULL, null)
                    .toJSONString();
        }
        if (null == record.getLphone() || record.getLphone().equals("")) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PHONE_NULL, null)
                    .toJSONString();
        }
        if (loginMapper.insert(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        /*
         *  校验传入对象结束
         */

        String mToken = TokenUtil.generateTokenAddMap(record);
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_SUCCESS_CONTENT, mToken, null)
                .toJSONString();
    }

    /**
     * 登陆接口
     */
    @Transactional
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String login(@RequestBody Login user) {
        if (null == user){
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_USER_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        Login checkData = loginMapper.selectByID(user.getId());
        if (checkData == null || !checkData.getId().equals(user.getId())){
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_ID_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_ID_UNCHECK_CODE_CONTENT, null)
                    .toJSONString();
        }
        //采用md5Hex方式加密密码传输
        if (!checkData.getLpassword().equals(user.getLpassword())) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_PASSWORD_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_PASSWORD_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        checkData.setLlivetime(new Date(System.currentTimeMillis()));
        TokenUtil.generateTokenAddMap(checkData);
        //更新数据库中用户的有效时间
        loginMapper.update(checkData);
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_USER_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    /**
     * 用户普通注销接口
     * 主要清除TokenUtils 中存放的Token
     * @param id
     * @return
     */
    @RequestMapping(value = "/logout/{id}", produces = "application/json;charset=UTF-8")
    public String logout(@PathVariable("id") int id){
        if (id < 1000) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        TokenUtil.removeToken(String.valueOf(id));
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    /**
     * 永久注销账号
     * 删除Login表中的用户的信息。
     *
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/permanentLogout/{id}", produces = "application/json;charset=UTF-8")
    public String deleteByID(@PathVariable("id") int id) {
        if (loginMapper.deleteByID(id) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    /**
     * 修改密码接口
     * @param record
     * @return
     */
    @Override
    @RequestMapping(value = "/changePassWord", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String updete(@RequestBody Login record) {
        if (null == record || loginMapper.update(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE_CONTENT, null)
                .toJSONString();

    }

    /**
     * 获取login表中所有用户的数据
     * @return
     */
    @Override
    @RequestMapping(value = "/getLoginAll", produces = "application/json;charset=UTF-8")
    public String selectAll() {
        List<Login> list = loginMapper.selectAll();
        if ((list) != null) {
            String token = TokenUtil.generateTokenAddMap(list.get(0));
            logger.info(token);
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                            ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, list)
                    .toJSONString();
        } else {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
    }

    /**
     * 根据 id 查询Login数据
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/getLogin/{id}", produces = "application/json;charset=UTF-8")
    public String selectByID(@PathVariable("id") int id) {
        logger.info("/getLogin/{id}");
        Login login = loginMapper.selectByID(id);
        if (null == login) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        } else {
            //TODO: 测试环境下获取测试用Token，上线环境下必须去除
            Token token = TokenUtil.generateLoginUserToken(login);
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                            ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT,
                            token.getToken(), login)
                    .toJSONString();
        }

    }

    /**
     * 检查该用户的是否有效存活。
     * @param id
     * @return
     */
    @RequestMapping(value = "/checkLiveTime/{id}", produces = "application/json;charset=UTF-8")
    public String checkLiveTime(@PathVariable("id") int id) {
        if (id < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE, "请求账号错误", null)
                    .toJSONString();
        }
        Date lastDate = loginMapper.liveTimeCheck(id);
        if (lastDate == null
                || ((System.currentTimeMillis() - lastDate.getTime()) >= CommonBean.liveTimeLimit))
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE,
                            ResponseCodeUtil.LEMONBILY_LOGIN_UNLIFE_CODE_CONTENT, null)
                    .toJSONString();
        else {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_LIFE_CODE,
                            ResponseCodeUtil.LEMONBILY_LOGIN_LIFE_CODE_CONTENT, null)
                    .toJSONString();
        }
    }
}
