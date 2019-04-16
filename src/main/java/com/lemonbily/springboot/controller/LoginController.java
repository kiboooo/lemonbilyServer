package com.lemonbily.springboot.controller;


import com.lemonbily.springboot.bean.CommonBean;
import com.lemonbily.springboot.bean.Token;
import com.lemonbily.springboot.entity.Login;
import com.lemonbily.springboot.mapper.LoginMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import com.lemonbily.springboot.util.TokenUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/LoginController")
public class LoginController extends BaseController<Login> {

    @Autowired(required = false)
    LoginMapper loginMapper;

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
        logger.info("-------- 注册接口校验传入对象开始 -------");
        /*
         *  校验传入对象开始
         */
        if (null == record) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (null == record.getLpassword() || record.getLpassword().equals("")) {
            logger.info("-------- 注册接口密码为空 -------");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PASS_NULL, null)
                    .toJSONString();
        }
        if (null == record.getLphone() || record.getLphone().equals("")) {
            logger.info("-------- 注册接口电话数据为空 -------");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PHONE_NULL, null)
                    .toJSONString();
        }

        if (loginMapper.insert(record) < 1) {
            logger.error("-------- 注册接口插入数据失败 -------");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }

        /*
         *  校验传入对象结束
         */
        logger.info("-------- 注册接口校验传入对象结束 -------");
        String mToken = TokenUtil.generateTokenAddMap(record);
        Login insertFinishData = loginMapper.selectByPhone(record.getLphone());
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_REGISTER_SUCCESS_CONTENT, mToken, insertFinishData)
                .toJSONString();
    }

    /**
     * 登陆接口
     */
    @Transactional
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String login(@RequestParam(value = "lphone", required = false) String lphone,
                        @RequestParam(value = "lpassword", required = false) String lpassword) {

        if (null == lphone || null == lpassword) {
            logger.info("登陆的电话 或者 密码为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_USER_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        Login checkData = loginMapper.selectByPhone(lphone);
        if (checkData == null || !checkData.getLphone().equals(lphone)) {
            logger.info("登陆的电话未存在");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_ID_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_ID_UNCHECK_CODE_CONTENT, null)
                    .toJSONString();
        }
        //采用md5Hex方式加密密码传输
        if (!checkData.getLpassword().equals(lpassword)) {
            logger.info("登陆的密码错误");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_PASSWORD_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_PASSWORD_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        checkData.setLlivetime(new Date(System.currentTimeMillis()));
        String mToken = TokenUtil.generateTokenAddMap(checkData);
        //更新数据库中用户的有效时间
        loginMapper.update(checkData);
        logger.info("登陆成功,token :" + mToken);
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_USER_SUCCESS_CODE_CONTENT, mToken, checkData)
                .toJSONString();
    }

    /**
     * 用户普通注销接口
     * 主要清除TokenUtils 中存放的Token
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/logout",
            method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String logout(@RequestParam(value = "id") int id) {
        if (id < 1000) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        TokenUtil.removeToken(String.valueOf(id));
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_LOGOUT_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    /**
     * 永久注销账号
     * 删除Login表中的用户的信息。
     *
     * @param id
     *  @return
     */
    @Override
    @RequestMapping(value = "/permanentLogout",
            method = RequestMethod.POST,  produces = "application/json;charset=UTF-8")
    public String deleteByID(int id) {
        if (loginMapper.deleteByID(id) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_PERMANENTLOGOUT_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }


    @Override
    public String update(Login record) {
        if (null == record ) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (loginMapper.update(record) < 1){
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    /**
     * 修改密码接口
     *
     * @param
     * @return
     */
    @Transactional
    @RequestMapping(value = "/changePassWord",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String changePassWord(@RequestParam(value = "id") int id,
                         @RequestParam(value = "lphone") String lphone,
                         @RequestParam(value = "oldPassWord") String oldPassWord,
                         @RequestParam(value = "newPassWord") String newPassWord) {
        if (loginMapper.checkPassWord(lphone, oldPassWord) <= 0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_LOGIN_OLDPASSWORD_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        Login record = new Login(id, newPassWord, lphone, null);
        return update(record);
    }

    /**
     * 获取login表中所有用户的数据
     *
     * @return
     */
    @Override
    @RequestMapping(value = "/getLoginAll",
            produces = "application/json;charset=UTF-8")
    public String selectAll() {
        List<Login> list = loginMapper.selectAll();
        if ((list) != null) {

            //TODO :测试用代码，认为注册并获取token
            String token = TokenUtil.generateTokenAddMap(list.get(2));
            logger.info(token);
            //测试结束

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
     *
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/getLogin",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String selectByID(int id) {
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
     *
     * @param LoginID
     * @return
     */
    @RequestMapping(value = "/checkLiveTime",
            method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String checkLiveTime( int LoginID) {
        if (LoginID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE, "请求账号错误", null)
                    .toJSONString();
        }
        Date lastDate = loginMapper.liveTimeCheck(LoginID);
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
