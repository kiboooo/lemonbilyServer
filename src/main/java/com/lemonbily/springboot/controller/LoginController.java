package com.lemonbily.springboot.controller;


import com.lemonbily.springboot.entity.Login;
import com.lemonbily.springboot.mapper.LoginMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LoginController {


    private static final long liveTimeLimit = 20*24*3600*1000;// day * hour * second
    @Autowired(required = false)
    LoginMapper loginMapper;

    @RequestMapping(value = "/getLoginAll", produces = "application/json;charset=UTF-8")
    public String getLoginAll() {
        List<Login> list= loginMapper.selectAll();
        if ((list) != null) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE, list)
                    .toJSONString();
        } else {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL)
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
                || ((System.currentTimeMillis() - lastDate.getTime()) >= liveTimeLimit))
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
