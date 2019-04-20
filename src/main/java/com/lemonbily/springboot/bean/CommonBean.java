package com.lemonbily.springboot.bean;

public class CommonBean {

    public static final long liveTimeLimit = 20*24*3600*1000;// 20day * hour * second

    //服务器上的环境地址
    public static final String SERVER_RELATIVE_PATH = "/lemonbily";
    public static final String SERVER_AVATAR_RELATIVE_PATH = "/lemonbily/Account/Avatar";

    //本地debug
    public static final String DEBUG_SERVER_AVATAR_RELATIVE_PATH= "g:\\lemonbily\\Account\\Avatar";

    //阿里云上存放图片静态资源的路径
    public static final String PICTURE_SERVER_PATH = "/lemonbily/picture/";
    public static final String VIDEO_SERVER_PATH = "/lemonbily/video/";

}
