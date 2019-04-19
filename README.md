# 后台说明文档

> Lemonbily Server 后台管理文档
>
> 后台服务程序地址说明：
>
> ip：47.107.46.0:80
>
> 部署路径：/lemonbily/Server
>
> 请求路径的前缀：47.107.46.0:80/lemonbily/**

### 共性：

正常回包status ：200

插入数据库语句执行，违反约束的回包 ： 500

正常请求回包的Body为：

```json
{
    "msg": ,	#信息描述
    "code": 0, 	#状态码，0为正常，大于0为错误状态
    "data": {	#回包所带的数据信息。
        "id": 1000,
        "llivetime": 1555661650275,
        "lpassword": "21232f297a57a5a743894a0e4a801fc3",
        "lphone": "12345678900"
    },
				#登录接口独有的用户登录成功的Token信息返回。
    "token": "f47e7cfafca3d3d50ae114ead7c8de9c"
}
```

### Token验证：

除了以下地址之外都需要进行Token验证（要求用户登录）：

```
"/LoginController/registered",
"/LoginController/login",
"/SeriesController/**",
"/VideoController/**
```

#### 携带Token请求方式：

在请求头加上

token ：0c56b96d17aeb5137bd326bf383db279 （登录成功后返回的Token）

phone：12345678900 （已经登录的账户电话）

账户失活的回包：

```json
{
    "msg": "账户已经失活，请重新登陆",
    "code": 1004
}
```

## login

> 登陆相关接口
>
> { prefix }/LoginController/**

#### register注册接口(不需要Token)

path：47.107.46.0:80/lemonbily/LoginControlle/registered

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
{
    "id": null,
     "llivetime": null, 
     "lpassword": "21232f297a57a5a743894a0e4a801fc3",
     "lphone": "12345678900"
}
```

##### 正常回包

```Json
{
    "msg": "账户注册成功",
    "code": 0,
    "data": {
        "id": 1019,
        "llivetime": 1555691622000,
        "lpassword": "818F5A8FD64FCEC0CF48D73EE7FBE2E2",
        "lphone": "12345678988"
    },
    "token": "09088b470fcc7afe78171731a9f0d1d8"
}
```

##### 出现错误的回包

```json
{
    "msg": "账户已经失活，请重新登陆", 	#出错的信息
    "code": 1004					#出错的状态码，通过Login状态码查询
}
```



#### login登录接口(不需要Token)

path：47.107.46.0:80/lemonbily/LoginControlle/login

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名    | 描述         |
| --------- | :----------- |
| lphone    | 登录的手机号 |
| lpassword | 登录密码     |

##### 成功回包

```json
{
    "msg": "登陆成功",
    "code": 0,
    "data": {
        "id": 1000,
        "llivetime": 1555663873661,
        "lpassword": "21232f297a57a5a743894a0e4a801fc3",
        "lphone": "12345678900"
    },
    "token": "01b8b4b43b5357b623cefa52c3fbcd31"
}
```

##### 失败回包

```json
{
    "msg": "登陆对象为空",
    "code": -1
}
{
    "msg": "登陆账号错误，请确认账户或前往注册",
    "code": 1001
}
{
    "msg": "登陆密码错误",
    "code": 1002
}
```



#### logout登出接口 （注销）

path：47.107.46.0:80/lemonbily/LoginControlle/logout

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述         |
| ------ | :----------- |
| lphone | 登录的手机号 |

##### 成功回包

```json
{
    "msg": "账户登出成功",
    "code": 0,
}
```

##### 失败回包

```json
{
    "msg": "账户登出失败",
    "code": 1008
}
```



#### permanentLogout 永久注销接口

path：47.107.46.0:80/lemonbily/LoginControlle/permanentLogout

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                     |
| ------ | :----------------------- |
| id     | 成功注册的用户被分配的id |

##### 成功回包

```json
{
    "msg": "账户永久注销成功",
    "code": 0,
}
```

##### 失败回包

```json
{
    "msg": "账户永久注销失败",
    "code": 1010
}
```



#### changePassWord修改密码接口

path：47.107.46.0:80/lemonbily/LoginControlle/changePassWord

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名      | 描述                     |
| ----------- | :----------------------- |
| id          | 成功注册的用户被分配的id |
| lphone      | 用户登录用的电话         |
| oldPassWord | 旧密码                   |
| newPassWord | 新密码                   |

##### 成功回包

```json
{
    "msg": "修改密码成功",
    "code": 0,
}
```

##### 失败回包

```json
{
    "msg": "修改密码失败",
    "code": 1052
}
```



#### getLoginAll获取表中所有用户

path：47.107.46.0:80/lemonbily/LoginControlle/getLoginAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "id": 1000,
            "llivetime": 1555664969000,
            "lpassword": "21232f297a57a5a743894a0e4a801fc3",
            "lphone": "12345678900"
        },
        {
            "id": 1001,
            "llivetime": 1554894678000,
            "lpassword": "b52029934c8312ad908b667ee5d611c6",
            "lphone": "18907891343"
        },
       ...
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": -1
}
```



### 关于login所有的状态码描述

```java

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
```

## Account

> 用户信息接口

##  Video

> 视频信息接口

## Series

> 剧集信息接口

## Buddy

> 关注好友信息接口

## Palcircle

> 交友圈动态信息接口

## Comment 

> 评论信息接口；
>
> 针对：视频，交友圈
>
> 区分Type: 
>
> ​	0:  交友圈
>
> ​	1：视频

## Like

> 点赞信息接口；
>
> 针对：交友圈

## Collect

> 收藏信息接口
>
> 针对：视频