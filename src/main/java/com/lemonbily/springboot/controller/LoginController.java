package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Login;
import com.lemonbily.springboot.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class LoginController {


    private static final long liveTimeLimit = 20*24*3600;// day * hour * second
    @Autowired(required = false) LoginMapper loginMapper;

    @RequestMapping("/getLoginAll")
    @ResponseBody
    public List<Login> getLoginAll() {
        return loginMapper.selectAll();
    }


    @RequestMapping("/checkLiveTime/{id}")
    @ResponseBody
    public boolean checkLiveTime(@PathVariable("id") int id) {
        if (id < 1000) {
            return false;
        }
        Date lastDate = loginMapper.liveTimeCheck(id);
        if (lastDate == null )
            return false;
        return (System.currentTimeMillis() - lastDate.getTime()) < liveTimeLimit;
    }

}
