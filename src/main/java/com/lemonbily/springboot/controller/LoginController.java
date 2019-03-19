package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Login;
import com.lemonbily.springboot.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController {

    @Autowired(required = false) LoginMapper loginMapper;
    @RequestMapping("/getLoginAll")
    @ResponseBody
    public List<Login> getLoginAll() {
        return loginMapper.selectAll();
    }
}
