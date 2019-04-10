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
    public static final String  LEMONBILY_SELECT_TABLE_NULL_CONTENT = "查找的表单为空";

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
    //登陆状态
    public static final int LEMONBILY_LOGIN_ID_ERRO_CODE = 1001;
    public static final int LEMONBILY_LOGIN_PASSWORD_ERRO_CODE = 1002;
    public static final String LEMONBILY_LOGIN_PASSWORD_ERRO_CODE_CONTENT = "登陆密码错误";
    public static final String LEMONBILY_LOGIN_USER_ERRO_CODE_CONTENT = "登陆对象为空";
    public static final String LEMONBILY_LOGIN_USER_SUCCESS_CODE_CONTENT = "登陆成功";
    public static final String LEMONBILY_LOGIN_ID_UNCHECK_CODE_CONTENT = "登陆账号错误，请确认账户或前往注册";
    //token有效性
    public static final int LEMONBILY_LOGIN_LIFE_CODE = 1003;
    public static final String LEMONBILY_LOGIN_LIFE_CODE_CONTENT = "账户存活有效";
    public static final int LEMONBILY_LOGIN_UNLIFE_CODE = 1004;
    public static final String LEMONBILY_LOGIN_UNLIFE_CODE_CONTENT = "账户已经失活，请重新登陆";
    //注册相关
    public static final int LEMONBILY_LOGIN_REGISTER_SUCCESS_CODE = 1005;
    public static final String LEMONBILY_LOGIN_REGISTER_SUCCESS_CONTENT = "账户注册成功";
    public static final int LEMONBILY_LOGIN_REGISTER_FAIL_CODE = 1006;
    public static final String LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_NULL = "账户注册失败，注册对象为空";
    public static final String LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PHONE_PRESENCE = "账户注册失败，注册电话已存在";
    public static final String LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_NAME_NULL = "账户注册失败，注册用户名为空";
    public static final String LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PASS_NULL = "账户注册失败，注册用户密码为空";
    public static final String LEMONBILY_LOGIN_REGISTER_FAIL_CONTENT_LOGIN_PHONE_NULL = "账户注册失败，注册电话为空";
    public static final int LEMONBILY_LOGIN_LOGOUT_SUCCESS_CODE = 1007;
    public static final String LEMONBILY_LOGIN_LOGOUT_SUCCESS_CODE_CONTENT = "账户登出成功";
    public static final int LEMONBILY_LOGIN_LOGOUT_FAIL_CODE = 1008;
    public static final String LEMONBILY_LOGIN_LOGOUT_FAIL_CODE_CONTENT = "账户登出失败";
    public static final int LEMONBILY_LOGIN_PERMANENTLOGOUT_SUCCESS_CODE = 1009;
    public static final String LEMONBILY_LOGIN_PERMANENTLOGOUT_SUCCESS_CODE_CONTENT = "账户永久注销成功";
    public static final int LEMONBILY_LOGIN_PERMANENTLOGOUT_FAIL_CODE = 1010;
    public static final String LEMONBILY_LOGIN_PERMANENTLOGOUT_FAIL_CODE_CONTENT = "账户永久注销失败";

    /**
     * 用户个人中心操作相关状态码
     * 状态码范围：1051-1100
     */
    public static final int LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE = 1051;
    public static final String LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE_CONTENT = "修改密码成功";
    public static final int LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE = 1052;
    public static final String LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE_CONTENT = "修改密码失败";

    //完善个人信息
    public static final String LEMONBILY_ACCOUNT_INSERT_SUCCESS_CONTENT = "插入ACCOUNT对象成功";
    public static final int LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE = 1053;
    public static final String LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT = "接收到的对象为空";
    public static final int LEMONBILY_ACCOUNT_AID_FAIL_CODE = 1054;
    public static final String LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT = "对象的ID是非法ID";
    public static final String LEMONBILY_ACCOUNT_UPDATE_SUCCESS_CONTENT = "更新ACCOUNT对象成功";
    public static final int LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE = 1055;
    public static final String LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT = "上传文件失败";





}
