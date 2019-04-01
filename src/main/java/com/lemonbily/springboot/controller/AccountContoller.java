package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/AccountContoller")
public class AccountContoller extends BaseController<Account> {
    @Override
    public String insert(Account record) {
        return null;
    }

    @Override
    public String deleteByID(int id) {
        return null;
    }

    @Override
    public String updete(Account record) {
        return null;
    }

    @Override
    public String selectAll() {
        return null;
    }

    @Override
    public String selectByID(int id) {
        return null;
    }
}
