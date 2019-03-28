package com.lemonbily.springboot.util;

public class ResponseCodeUtil {

    /**
     * 数据库操作相关状态码
     * 状态码范围：1-1000
     */
    public static final int LEMONBILY_SELECT_ERRO_CODE = 1;
    public static final String LEMONBILY_SELECT_ERRO_CODE_CONTENT = "数据库查询错误";
    public static final int LEMONBILY_INSERT_ERRO_CODE = 2;
    public static final String LEMONBILY_INSERT_ERRO_CODE_CONTENT = "数据库插入错误";
    public static final int LEMONBILY_UPDATE_ERRO_CODE = 3;
    public static final String LEMONBILY_UPDATE_ERRO_CODE_CONTENT = "数据库更新错误";
    public static final int LEMONBILY_DELETE_ERRO_CODE = 4;
    public static final String LEMONBILY_DELETE_ERRO_CODE_CONTENT = "数据库删除错误";

    /**
     * 登陆操作相关状态码
     * 状态码范围：1001-1050
     */
    public static final int LEMONBILY_LOGIN_ID_ERRO_CODE = 1001;
    public static final String LEMONBILY_LOGIN_ID_ERRO_CODE_CONTENT = "登陆用户名错误";
    public static final int LEMONBILY_LOGIN_PASSWORD_ERRO_CODE = 1002;
    public static final String LEMONBILY_LOGIN_PASSWORD_ERRO_CODE_CONTENT = "登陆密码错误";




}
