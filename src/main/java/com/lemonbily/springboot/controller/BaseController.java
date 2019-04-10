package com.lemonbily.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public abstract class BaseController<T> {
    //插入数据
    public abstract String insert(T record);

    //删除数据
    public abstract String deleteByID(int id);

    //更改数据
    public abstract String update(T record);

    //批量查询
    public abstract String selectAll();

    //根据ID查询
    public abstract String selectByID(int id);
}
