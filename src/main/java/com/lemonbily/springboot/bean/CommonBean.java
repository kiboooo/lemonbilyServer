package com.lemonbily.springboot.bean;

public class CommonBean {

    public static final long liveTimeLimit = 20*24*3600*1000;// 20day * hour * second

    //服务器上的环境地址
    public static final String SERVER_RELATIVE_PATH = "/lemonbily";
    public static final String SERVER_AVATAR_RELATIVE_PATH = "/lemonbily/Account/Avatar";

    //本地debug
    public static final String DEBUG_SERVER_AVATAR_RELATIVE_PATH= "g:\\lemonbily\\Account\\Avatar";

}
