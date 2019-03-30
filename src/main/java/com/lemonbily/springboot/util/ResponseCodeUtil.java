package com.lemonbily.springboot.util;

public class ResponseCodeUtil {

    /**
     * 所有请求回包的通用状态码
     */
    public static final int LEMONBILY_SUCCESS_CODE = 0;
    public static final String LEMONBILY_SUCCESS_CODE_CONTENT = "正常回包";
    public static final int LEMONBILY_FAIL_CODE = -1;
    public static final String LEMONBILY_FAIL_CODE_CONTENT = "失败回包";

    /**
     * 数据库操作相关状态码
     * 状态码范围：1-1000
     */
    //查找
    public static final int LEMONBILY_SELECT_ERRO_CODE = 1;
    public static final String LEMONBILY_SELECT_ERRO_CODE_CONTENT = "数据库查询错误";
    public static final String LEMONBILY_SELECT_TABLE_NULL = "查找的表单为空";

    //插入
    public static final int LEMONBILY_INSERT_ERRO_CODE = 2;
    public static final String LEMONBILY_INSERT_ERRO_CODE_CONTENT = "数据库插入错误";

    //更新
    public static final int LEMONBILY_UPDATE_ERRO_CODE = 3;
    public static final String LEMONBILY_UPDATE_ERRO_CODE_CONTENT = "数据库更新错误";

    //删除
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
    public static final int LEMONBILY_LOGIN_LIFE_CODE = 1003;
    public static final String LEMONBILY_LOGIN_LIFE_CODE_CONTENT = "账户存活有效";
    public static final int LEMONBILY_LOGIN_UNLIFE_CODE = 1004;
    public static final String LEMONBILY_LOGIN_UNLIFE_CODE_CONTENT = "账户已经失活，请重新登陆";



}
