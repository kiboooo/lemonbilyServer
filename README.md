# 后台说明文档

> Lemonbily Server 后台管理文档
>

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
"/VideoController/**,
"/error/**","/picture/**","/video/**","/Account/**",
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

path：{ ServerPath }/lemonbily/LoginControlle/registered

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

path：{ ServerPath }/lemonbily/LoginControlle/login

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

path：{ ServerPath }/lemonbily/LoginControlle/logout

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

path：{ ServerPath }/lemonbily/LoginControlle/permanentLogout

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

path：{ ServerPath }/lemonbily/LoginControlle/changePassWord

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

path：{ ServerPath }/lemonbily/LoginControlle/getLoginAll

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
>
> { prefix }/AccountController/**

#### insert插入个人信息

path：{ ServerPath }/lemonbily/AccountController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
{
            "aavatar": "xxx.jpg", 	#头像存储
            "aid": 1000,
            "aname": "admin",		#账号的用户名
            "asex": "male"			#性格
        }
```

##### 正常回包

```Json
{
    "msg": "插入ACCOUNT对象成功",
    "code": 0,
    "data": {
        "aavatar": "xxx.jpg", 	#头像存储
            "aid": 1000,		#账号的loginid
            "aname": "admin",	#账号的用户名
            "asex": "male"		#性格
    }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "对象的ID是非法ID", 	
    "code": 1054					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据ID删除

path：{ ServerPath }/lemonbily/AccountController/deleteByID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                        |
| ------ | :------------------------------------------ |
| aid    | 成功注册的用户被分配的id,作为Accout表的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": "头像图片删除完毕"
}
{
    "msg": "正常回包",
    "code": 0,
    "data": "头像图片删除失败，或路径不存在"
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4,
    "data": "获取的Account对象为空,无法删除"
}
{
    "msg": "对象的ID是非法ID",
    "code": -1
}
```



#### update由Json对象更新表内容

path：{ ServerPath }/lemonbily/AccountController/update

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
#根据表中的字段，如果该字段的数据不为Null就更新数据
{
            "aavatar": "xxx.jpg", 	#头像存储
            "aid": 1000,
            "aname": "admin",		#账号的用户名
            "asex": "male"			#性格
        }
```

##### 正常回包

```Json
{
    "msg": "更新ACCOUNT对象成功",
    "code": 0,
    "data": {
        "aavatar": "xxx.jpg", 	#头像存储
            "aid": 1000,		#账号的loginid
            "aname": "admin",	#账号的用户名
            "asex": "male"		#性格
    }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库更新错误", 	
    "code": 3					
}
{
    "msg": "对象的ID是非法ID", 	
    "code": -1					
}
{
    "msg": "接收到的对象为空", 	
    "code": 3					
}
```



#### uploadAvatar上传头像接口

> 服务器上头像的存储地址：/lemonbily/Account/Avatar

path：{ ServerPath }/lemonbily/AccountController/uploadAvatar

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名      | 描述                                        |
| ----------- | :------------------------------------------ |
| avatarImage | 上传文件的 MultipartFile 对象。             |
| aid         | 成功注册的用户被分配的id,作为Accout表的外键 |

##### 成功回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data": ".../.../xxx.png" 	#存储在服务器上的地址
}
```

##### 出现错误的回包

```json
{
    "msg": "上传文件失败", 	
    "code": 1055					
}
{
    "msg": "对象的ID是非法ID", 	
    "code": -1					
}
{
    "msg": "接收到的对象为空", 	
    "code": 1053					
}
```



#### uploadAvatars批量上传头像接口

> 服务器上头像的存储地址：/lemonbily/Account/Avatar

path：{ ServerPath }/lemonbily/AccountController/uploadAvatars

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名       | 描述                                        |
| ------------ | :------------------------------------------ |
| avatarImages | 上传文件的 MultipartFile的数组对象。        |
| aid          | 成功注册的用户被分配的id,作为Accout表的外键 |

##### 成功回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "msg": "上传文件失败",
            "code": 1055,
            "data": "3e528a73ac7cb25b.jpg"
        },
        {
            "msg": "上传文件失败",
            "code": 1055,
            "data": "69feeabe0ccbbbe2.jpg"
        },
        {
            "msg": "上传文件失败",
            "code": 1055,
            "data": "371fe40fb256e460.jpg"
        }
    ]
}
or
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "msg": "正常回包",
            "code": 0,
            "data": "/lemonbily/Account/Avatar/1000/avatar_1000_20190420164719648.jpg"
        },
        {
            "msg": "正常回包",
            "code": 0,
            "data": "/lemonbily/Account/Avatar/1000/avatar_1000_20190420164719649.jpg"
        },
        {
            "msg": "正常回包",
            "code": 0,
            "data": "/lemonbily/Account/Avatar/1000/avatar_1000_20190420164719651.jpg"
        }
    ]
}
```

##### 出现错误的回包

```json
{
    "msg": "上传文件失败", 	
    "code": 1055					
}
{
    "msg": "对象的ID是非法ID", 	
    "code": -1					
}
{
    "msg": "接收到的对象为空", 	
    "code": 1053					
}
```





#### selectAll获取Account表的数据

path：{ ServerPath }/lemonbily/AccountController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "aavatar": "xxx.jpg",
            "aid": 1000,
            "aname": "admin",
            "asex": "male"
        },
        {
            "aavatar": "xxx.jpg",
            "aid": 1001,
            "aname": "kibo",
            "asex": "female"
        },
        ...
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByID根据ID获取Accout对象

path：{ ServerPath }/lemonbily/AccountController/selectByID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                        |
| ------ | :------------------------------------------ |
| aid    | 成功注册的用户被分配的id,作为Accout表的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
     "data": {
            "aavatar": "xxx.jpg",
            "aid": 1000,
            "aname": "admin",
            "asex": "male"
        }
}
```

##### 失败回包

```json
{
    "msg": "对象的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



### 关于AccountController操作的所有状态码：

```java
/**
     * 用户个人中心操作相关状态码
     * 状态码范围：1051-1100
     */
    public static final int LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE = 1051;
    public static final String LEMONBILY_LOGIN_CHANGE_PASSWORD_SUCCESS_CODE_CONTENT = "修改密码成功";
    public static final int LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE = 1052;
    public static final String LEMONBILY_LOGIN_CHANGE_PASSWORD_FAIL_CODE_CONTENT = "修改密码失败";
    public static final String LEMONBILY_LOGIN_OLDPASSWORD_FAIL_CODE_CONTENT = "输入的旧密码错误";

    //完善个人信息
    public static final String LEMONBILY_ACCOUNT_INSERT_SUCCESS_CONTENT = "插入ACCOUNT对象成功";
    public static final int LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE = 1053;
    public static final String LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT = "接收到的对象为空";
    public static final int LEMONBILY_ACCOUNT_AID_FAIL_CODE = 1054;
    public static final String LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT = "对象的ID是非法ID";
    public static final String LEMONBILY_ACCOUNT_UPDATE_SUCCESS_CONTENT = "更新ACCOUNT对象成功";
    public static final int LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE = 1055;
    public static final String LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT = "上传文件失败";
```



##  Video（无需Token）

> 视频信息接口
>
> { prefix }/VideoController/**

#### insert插入视频信息接口

path：{ ServerPath }/lemonbily/VideoController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
{
            "aavatar": "xxx.jpg", 	#头像存储
            "aid": 1000,
            "aname": "admin",		#账号的用户名
            "asex": "male"			#性格
        }
```

##### 正常回包

```Json
{
    "msg": "插入ACCOUNT对象成功",
    "code": 0,
    "data": {
        "aavatar": "xxx.jpg", 	#头像存储
            "aid": 1000,		#账号的loginid
            "aname": "admin",	#账号的用户名
            "asex": "male"		#性格
    }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "对象的ID是非法ID", 	
    "code": 1054					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID 根据视频的ID删除视频资源

path：{ ServerPath }/lemonbily/VideoController/deleteByID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| vid    | 视频表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": "视频,图片资源删除完毕"
}
{
    "msg": "正常回包",
    "code": 0,
    "data": "视频,图片资源删除失败，或路径不存在"
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4,
    "data": "获取的video对象为空,无法删除"
}
{
    "msg": "对象的ID是非法ID",
    "code": -1
}
```



#### update更新视频数据

path：{ ServerPath }/lemonbily/VideoController/update

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
#根据表中的字段，如果该字段的数据不为Null就更新数据
 {
            "sid": 1,					#外键，对应剧集Series表的ID
            "vdescribe": "两分钟看完新海诚七部经典作品。",
            "vid": null,
            "vname": "两分钟看完新海诚七部经典作品",
            "vpath": "/lemonbily/video/music_05_xhcdm.mp4",
            "vpicture": "/lemonbily/picture/music_05.jpg",
            "vplaynum": 99,
            "vtimer": "00 :02:28"
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "sid": 1,
            "vdescribe": "两分钟看完新海诚七部经典作品。",
            "vid": 1,
            "vname": "两分钟看完新海诚七部经典作品",
            "vpath": "/lemonbily/video/music_05_xhcdm.mp4",
            "vpicture": "/lemonbily/picture/music_05.jpg",
            "vplaynum": 99,
            "vtimer": "00 :02:28"
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库更新错误", 	
    "code": 3					
}
{
    "msg": "接收到的对象为空", 	
    "code": 3					
}
{
    "msg": "更新的VID是非法ID", 	
    "code": 1101					
}
```



#### updateAddPlay更新视频的观看数

path：{ ServerPath }/lemonbily/VideoController/updateAddPlay

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| vid    | 视频表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": "vid is 99"
}
```



##### 失败回包

```json
{
    "msg": "数据库更新错误",
    "code": 3
}
{
    "msg": "更新的VID是非法ID",
    "code": 1101
}
```



#### selectAll获取Video表所有数据

path：{ ServerPath }/lemonbily/VideoController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "sid": 1,
            "vdescribe": "两分钟看完新海诚七部经典作品。",
            "vid": 1,
            "vname": "两分钟看完新海诚七部经典作品",
            "vpath": "/lemonbily/video/music_05_xhcdm.mp4",
            "vpicture": "/lemonbily/picture/music_05.jpg",
            "vplaynum": 99,
            "vtimer": "00 :02:28"
        },
        {
            "sid": 2,
            "vdescribe": "《死侍2》全新加料版预告片震撼来袭。历经两年的漫长等待,",
            "vid": 2,
            "vname": "《死侍2:我爱我家》定档预告",
            "vpath": "/lemonbily/video/movie_01_ss.mp4",
            "vpicture": "/lemonbily/picture/movie_01.jpg",
            "vplaynum": 1152,
            "vtimer": "00:02:15"
        },
        {
            "sid": 3,
            "vdescribe": "李宗盛现场演唱这些经典歌曲，超级好听！",
            "vid": 3,
            "vname": "李宗盛经典歌曲合集",
            "vpath": "/lemonbily/video/music_02_lzshj.mp4",
            "vpicture": "/lemonbily/picture/music_02.jpg",
            "vplaynum": 55,
            "vtimer": "00:04:36"
        },
        {
            "sid": 4,
            "vdescribe": "这是一首新写的旧歌，是一个埋藏在大哥心里多年的心事。",
            "vid": 4,
            "vname": "李宗盛《新写的旧歌》",
            "vpath": "/lemonbily/video/music_03_xxdjg.mp4",
            "vpicture": "/lemonbily/picture/music_03.jpg",
            "vplaynum": 66,
            "vtimer": "00:06:21"
        },
        ...
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByVID根据VID获取Video对象

path：{ ServerPath }/lemonbily/VideoController/selectByVID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| vid    | 视频表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "sid": 4,
            "vdescribe": "这是一首新写的旧歌，是一个埋藏在大哥心里多年的心事。",
            "vid": 4,
            "vname": "李宗盛《新写的旧歌》",
            "vpath": "/lemonbily/video/music_03_xxdjg.mp4",
            "vpicture": "/lemonbily/picture/music_03.jpg",
            "vplaynum": 66,
            "vtimer": "00:06:21"
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectBySID根据SID获取Video数组对象

path：{ ServerPath }/lemonbily/VideoController/selectBySID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                       |
| ------ | :------------------------- |
| sid    | 剧集表被分配的sid,作为外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [{
            "sid": 3,
            "vdescribe": "李宗盛现场演唱这些经典歌曲，超级好听！",
            "vid": 3,
            "vname": "李宗盛经典歌曲合集",
            "vpath": "/lemonbily/video/music_02_lzshj.mp4",
            "vpicture": "/lemonbily/picture/music_02.jpg",
            "vplaynum": 55,
            "vtimer": "00:04:36"
        },
             {
            "sid": 3,
            "vdescribe": "《死侍2》全新加料版预告片震撼来袭。历经两年的漫长等待,",
            "vid": 2,
            "vname": "《死侍2:我爱我家》定档预告",
            "vpath": "/lemonbily/video/movie_01_ss.mp4",
            "vpicture": "/lemonbily/picture/movie_01.jpg",
            "vplaynum": 1152,
            "vtimer": "00:02:15"
        }
]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



### 状态码

```java
 /**
     * 视频列表操作相关状态码
     * 状态码范围： 1101-1300
     */

    public static final int LEMONBILY_VIDEO_ID_ILLEGAL_CODE = 1101;
    public static final String LEMONBILY_VIDEO_INSERT_SID_ILLEGAL_CODE_CONTENT = "插入的SID是非法ID";
    public static final String LEMONBILY_VIDEO_UPDATE_VID_ILLEGAL_CODE_CONTENT = "更新的VID是非法ID";
```



## Series（无需Token）

> 剧集信息接口
>
> { prefix }/SeriesController/**

#### insert剧集信息插入

path：{ ServerPath }/lemonbily/SeriesController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
{
    "sdescribe": "两分钟看完新海诚七部经典作品", #简介
    "seriesid": null,						#主键ID，自动分配
    "sname": "两分钟看完新海诚七部经典作品"，	  #名字
    "snum": 1,								#剧集数量
    "stype": 0								#-1：无类别；0：音乐MV；1：电视剧；2：电影
 }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data": {
            "sdescribe": "《死侍2》全新加料版预告片震撼来袭",
            "seriesid": 2,
            "sname": "《死侍2:我爱我家》定档预告",
            "snum": 3,
            "stype": 2
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "接收的type是非法类型", 	
    "code": 1301					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据SID删除数据

path：{ ServerPath }/lemonbily/SeriesController/deleteByID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| sid    | 剧集表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "删除的ID是非法ID",
    "code": 4
}
```



#### update更新剧集数据

path：{ ServerPath }/lemonbily/SeriesController/update

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
{
            "sdescribe": "两分钟看完新海诚七部经典作品",
            "seriesid": 1,
            "sname": "两分钟看完新海诚七部经典作品",
            "snum": 1,
            "stype": 0
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "sdescribe": "两分钟看完新海诚七部经典作品",
            "seriesid": 1,
            "sname": "两分钟看完新海诚七部经典作品",
            "snum": 1,
            "stype": 0
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库更新错误", 	
    "code": 3					
}
{
    "msg": "接收到的对象为空", 	
    "code": 3					
}
{
    "msg": "更新的SID是非法ID", 	
    "code": 1301					
}
```



#### selectAll 获取Series表的所有数据

path：{ ServerPath }/lemonbily/SeriesController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "sdescribe": "两分钟看完新海诚七部经典作品",
            "seriesid": 1,
            "sname": "两分钟看完新海诚七部经典作品",
            "snum": 1,
            "stype": 0
        },
        {
            "sdescribe": "《死侍2》全新加料版预告片震撼来袭",
            "seriesid": 2,
            "sname": "《死侍2:我爱我家》定档预告",
            "snum": 3,
            "stype": 2
        },
        ....
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByID根据SID获取Serise对象

path：{ ServerPath }{ ServerPath }/lemonbily/SeriesController/selectBySID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                       |
| ------ | :------------------------- |
| sid    | 剧集表被分配的sid,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "sdescribe": "《死侍2》全新加料版预告片震撼来袭",
            "seriesid": 2,
            "sname": "《死侍2:我爱我家》定档预告",
            "snum": 3,
            "stype": 2
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



### 状态码

```java
/**
     * 剧集列表操作相关状态码
     * 状态码范围： 1301-1400
     */
    public static final int LEMONBILY_SERIES_ID_ILLEGAL_CODE = 1301;
    public static final String LEMONBILY_SERIES_TEPY_ILLEGAL_CONTENT = "接收的type是非法类型";
    public static final String LEMONBILY_SERIES_UPDATE_SID_ILLEGAL_CONTENT = "更新的SID是非法ID";
```



## Buddy

> 关注好友信息接口
>
> { prefix }/BuddyController/**
>
> 存储的好友表的关系为互为好友表的关系

#### insert关注好友接口

> 在使用该接口之前，必须先查询好友表，保证非好友；
>
> 因为该接口不做好友验证；

path：{ ServerPath }/lemonbily/BuddyController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
#例子
#要求！#要求！#要求！
# buddyid 必须要大于 userid ，保证互为好友关系的一致性
{
            "bid": null,   		# 主键
            "buddyid": 1001,	#关注好友的ID，基于LoginID的外键
            "userid": 1000		#主动方的用户ID，基于LoginID的外键
    
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data": {
            "bid": 2,
            "buddyid": 1002,
            "userid": 1000
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "接收的UserID是非法ID", 	
    "code": 1801					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据BID解除好友关系

path：{ ServerPath }/lemonbily/BuddyController/deleteByBID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| bid    | 好友表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "删除的ID是非法ID",
    "code": 4
}
```



#### deleteByUIDandBuddlyID根据UID 和 BuddlyID 解除好友关系

path：{ ServerPath }/lemonbily/BuddyController/deleteByUIDandBuddlyID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名  | 描述                              |
| ------- | :-------------------------------- |
| userID  | 主动方的用户ID ,基于LoginID的外键 |
| buddyID | 被动方的用户ID,基于LoginID的外键  |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "接收的UserID是非法ID",
    "code": 4
}
```



#### selectAll获取Buddy表所有好友数据

path：{ ServerPath }/lemonbily/BuddyController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "bid": 1,
            "buddyid": 1001,
            "userid": 1000
        },
        {
            "bid": 2,
            "buddyid": 1002,
            "userid": 1000
        },
        {
            "bid": 3,
            "buddyid": 1017,
            "userid": 1000
        }
      ...
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByID根据好友表主键获取好友关系

path：{ ServerPath }/lemonbily/BuddyController/selectByBID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                       |
| ------ | :------------------------- |
| bid    | 好友表被分配的bid,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "bid": 1,
            "buddyid": 1001,
            "userid": 1000
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByUserID获取ID用户的好友关系表

path：{ ServerPath }/lemonbily/BuddyController/selectByUserID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                 |
| ------ | :------------------- |
| userID | 查询好友关系的用户ID |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "aavatar": "xxx.jpg",
            "aid": 1001,
            "aname": "kibo",
            "asex": "female"
        },
        {
            "aavatar": "xxx.jpg",
            "aid": 1002,
            "aname": "dayday",
            "asex": "female"
        },
        {
            "aavatar": "sdfsdf.png",
            "aid": 1017,
            "aname": "jjjj",
            "asex": "m"
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



### 状态码

```java
 /**
     * 关注好友操作相关状态码
     * 状态码范围：1801-1900
     */

    public static final int LEMONBILY_BUDDY_ILLEGAL_CODE = 1801;
    public static final String LEMONBILY_BUDDY_USERID_ILLEGAL_CONTENT = "接收的UserID是非法ID";

```



## Palcircle

> 交友圈动态信息接口
>
> { prefix }/PalcircleController/**

#### insert发布动态接口

path：{ ServerPath }/lemonbily/PalcircleController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
 {
            "palcontent": "冲就完事了！",
            "palid": null,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "palcontent": "冲就完事了！",
            "palid": null,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "接收的PALUSERID是非法ID", 	
    "code": 1501					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据朋友圈ID删除数据

path：{ ServerPath }/lemonbily/PalcircleController/deleteByPalID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                        |
| ------ | :-------------------------- |
| palID  | 交友圈表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "删除的ID是非法ID",
    "code": 4
}
```



#### update更新对应朋友圈的数据

path：{ ServerPath }/lemonbily/PalcircleController/update

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
 {
            "palcontent": "冲就完事了！",
            "palid": 1,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "palcontent": "冲就完事了！",
            "palid": null,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库更新错误", 	
    "code": 3					
}
{
    "msg": "接收到的对象为空", 	
    "code": 3					
}
{
    "msg": "接收的PALID是非法ID", 	
    "code": 1501					
}
```



#### updateLikeNumber更新朋友的圈的点赞数

path：{ ServerPath }/lemonbily/PalcircleController/updateLikeNumber

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名   | 描述                        |
| -------- | :-------------------------- |
| likeType | 1：自增；-1：自减           |
| palID    | 交友圈表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0 ,
    "data":"likeType is : 188"
}
```



##### 失败回包

```json
{
    "msg": "数据库更新错误", 	
    "code": 3					
}
{
    "msg": "接收的PALID是非法ID", 	
    "code": 1501					
}
```



#### selectAll获取所有动态接口

path：{ ServerPath }/lemonbily/PalcircleController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "palcontent": "冲就完事了！",
            "palid": 1,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByID根据主键ID获取数据

path：{ ServerPath }/lemonbily/PalcircleController/selectByPalID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                           |
| ------ | :----------------------------- |
| palID  | 交友圈表被分配的palID,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":   {
            "palcontent": "冲就完事了！",
            "palid": 1,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByPalUserID根据用户ID获取数据

path：{ ServerPath }/lemonbily/PalcircleController/selectByPalUserID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名    | 描述             |
| --------- | :--------------- |
| palUserID | 发送动态的用户ID |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "palcontent": "冲就完事了！",
            "palid": 1,
            "pallicknum": 99,
            "paltime": 1553166281000,
            "paluserid": 1000
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



### 状态码

```java
 /**
     * 交友圈动态操作相关状态码
     * 状态码范围：1501-1600
     */

    public static final int LEMONBILY_PALCIRCLE_ILLEGAL_CODE = 1501;
    public static final String LEMONBILY_PALCIRCLE_PALID_ILLEGAL_CONTENT = "接收的PALID是非法ID";
    public static final String LEMONBILY_PALCIRCLE_PALUSER_ID_ILLEGAL_CONTENT = "接收的PALUSERID是非法ID";
```



## Comment 

> 评论信息接口；
>
> { prefix }/CommentController/**
>
> 针对：视频，交友圈
>
> 区分Type: 
>
> ​	0:  交友圈
>
> ​	1：视频



#### insert评论发表

path：{ ServerPath }/lemonbily/CommentController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
{
            "comcontent": "真实感人Q！",		#评论的内容	
            "comid": null,					#主键
            "comtime": 1581935000,			#评论的时间
            "comtype": 1,					#评论的对象类型：0-交友圈；1-视频
            "toid": 1,						#被评论对象的主键ID
            "uid": 1000						#发表评论的用户
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "comcontent": "真实感人Q！",
            "comid": 1,
            "comtime": 1581935000,
            "comtype": 1,
            "toid": 1,
            "uid": 1000
        }
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "接收的UID是非法ID", 	
    "code": 1401					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据评论主键ID删除数据

path：{ ServerPath }/lemonbily/CommentController/deleteByID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| comID  | 评论表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "删除的ID是非法ID",
    "code": 4
}
```



#### selectAll获取所有评论数据

path：{ ServerPath }/lemonbily/CommentController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "comcontent": "真实感人Q！",
            "comid": 1,
            "comtime": 1581935000,
            "comtype": 1,
            "toid": 1,
            "uid": 1000
        },
        {
            "comcontent": "小贱贱真牛逼",
            "comid": 2,
            "comtime": 1581935000,
            "comtype": 1,
            "toid": 5,
            "uid": 1001
        },
        {
            "comcontent": "小贱贱真牛sdfasdfasd逼",
            "comid": 4,
            "comtime": 1555109891000,
            "comtype": 1,
            "toid": 4,
            "uid": 1001
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByID根据评论表主键ID

path：{ ServerPath }/lemonbily/CommentController/selectByComID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| comID  | 评论表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":    {
            "comcontent": "小贱贱真牛逼",
            "comid": 2,
            "comtime": 1581935000,
            "comtype": 1,
            "toid": 5,
            "uid": 1001
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByUID根据用户ID

path：{ ServerPath }/lemonbily/CommentController/selectByUID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                |
| ------ | :---------------------------------- |
| palID  | 发表评论用户的ID，基于LoginID的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":    {
            "comcontent": "小贱贱真牛逼",
            "comid": 2,
            "comtime": 1581935000,
            "comtype": 1,
            "toid": 5,
            "uid": 1001
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByUIDAndType根据评论对象类型和用户ID

> 可以获取用户对于某个项目（如：视频）的所有评论

path：{ ServerPath }/lemonbily/CommentController/selectByUIDAndType

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名  | 描述                                |
| ------- | :---------------------------------- |
| uid     | 发表评论用户的ID，基于LoginID的外键 |
| comtype | 评论对象的类型；0-交友圈；1-视频    |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "comcontent": "小贱贱真牛逼",
            "comid": 2,
            "comtime": 1581935000,
            "comtype": 1,
            "toid": 5,
            "uid": 1001
        },
        {
            "comcontent": "小贱贱真牛sdfasdfasd逼",
            "comid": 4,
            "comtime": 1555109891000,
            "comtype": 1,
            "toid": 4,
            "uid": 1001
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
{
    "msg": "接收的comType是非法comType",
    "code": 1401
}
```



#### selectByUIDAndTypeAndToID根据评论类型，用户ID，评论对象ID

path：{ ServerPath }/lemonbily/CommentController/selectByUIDAndTypeAndToID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名  | 描述                                           |
| ------- | :--------------------------------------------- |
| uid     | 发表评论用户的ID，基于LoginID的外键            |
| comtype | 评论对象的类型；0-交友圈；1-视频               |
| toid    | 被评论对象的ID，根据类型对应不同的表的主键ID； |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "comcontent": "小贱贱真牛sdfasdfasd逼",
            "comid": 4,
            "comtime": 1555109891000,
            "comtype": 1,
            "toid": 4,
            "uid": 1001
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
{
    "msg": "接收的ToID是非法ToID",
    "code": 1401
}

```



### 状态码

```java
   /**
     * 评论列表操作相关状态码
     * 状态码范围： 1401-1500
     */
    public static final int LEMONBILY_COMMENT_ID_ILLEGAL_CODE = 1401;
    public static final String LEMONBILY_COMMENT_UID_ILLEGAL_CONTENT = "接收的UID是非法ID";
    public static final String LEMONBILY_COMMENT_TYPE_ILLEGAL_CONTENT = "接收的comType是非法comType";
    public static final String LEMONBILY_COMMENT_TOID_ILLEGAL_CONTENT = "接收的ToID是非法ToID";
```



## Like

> 点赞信息接口；
>
> { prefix }/LikeController/**
>
> 针对：交友圈



#### inser点赞操作接口

path：{ ServerPath }/lemonbily/LikeController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
{
            "likeid": null,		#主键
            "ltopalid": 1,	 	#动态的id
            "luserid": 1000		#用户的ID
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "likeid": 1,
            "ltopalid": 1,
            "luserid": 1000
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "接收的UserID是非法ID", 	
    "code": 1601					
}
{
    "msg": "接收的PALID是非法ID", 	
    "code": 1601					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据主键删除

path：{ ServerPath }/lemonbily/LikeController/deleteByLikeID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| likeID | 点赞表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "接收的PALID是非法ID",
    "code": 4
}
{
    "msg": "接收的UserID是非法ID",
    "code": 4
}
```



#### deleteByUserIDAndPalID根据点赞用户和被点赞朋友圈的ID

path：{ ServerPath }/lemonbily/LikeController/deleteByUserIDAndPalID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                    |
| ------ | :-------------------------------------- |
| userID | 执行点赞操作的用户ID，基于LoginID的外键 |
| palID  | 被点赞的动态，基于交友圈的ID的外键      |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "接收的ColID是非法ID",
    "code": 4
}
{
    "msg": "接收的VID是非法ID",
    "code": 1701
}
```

#### selectAll

path：{ ServerPath }/lemonbily/LikeController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "likeid": 1,
            "ltopalid": 1,
            "luserid": 1000
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```

#### selectByLikeID根据主键获取

path：{ ServerPath }/lemonbily/LikeController/selectByLikeID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| likeID | 点赞表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": {
        "likeid": 1,
        "ltopalid": 1,
        "luserid": 1000
    }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByUserID根据点赞的用户获取

path：{ ServerPath }/lemonbily/LikeController/selectByUserID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                    |
| ------ | :-------------------------------------- |
| userID | 执行点赞操作的用户ID，基于LoginID的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "likeid": 1,
            "ltopalid": 1,
            "luserid": 1000
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByPalID根据被点赞动态的ID获取

path：{ ServerPath }/lemonbily/LikeController/selectByPalID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                               |
| ------ | :--------------------------------- |
| palID  | 被点赞的动态，基于交友圈的ID的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "likeid": 1,
            "ltopalid": 1,
            "luserid": 1000
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByUserIDAndPalID根据被点赞动态的ID 和 点赞用户的ID获取

path：{ ServerPath }/lemonbily/LikeController/selectByUserIDAndPalID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                    |
| ------ | :-------------------------------------- |
| userID | 执行点赞操作的用户ID，基于LoginID的外键 |
| palID  | 被点赞的动态，基于交友圈的ID的外键      |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": {
            "colid": 1001,
            "colpid": 2,
            "vid": 1
    }
}
```

##### 失败回包

```json
{
    "msg": "接收的UserID是非法ID",
    "code": 1
}
{
    "msg": "接收的PALID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectLikeNumberByPalID获取某条动态的点赞数

path：{ ServerPath }/lemonbily/LikeController/selectCollectNumberByVID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                               |
| ------ | :--------------------------------- |
| palID  | 被点赞的动态，基于交友圈的ID的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": 1 	#被收藏数量
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
```



### 状态码

```java

/**
     * 点赞操作相关状态码
     * 状态码范围：1601-1700
     */

    public static final int LEMONBILY_LIKE_ILLEGAL_CODE = 1601;
    public static final String LEMONBILY_LIKE_USERID_ILLEGAL_CONTENT = "接收的UserID是非法ID";
    public static final String LEMONBILY_LIKE_PALID_ILLEGAL_CONTENT = "接收的PALID是非法ID";

```



## Collect

> 收藏信息接口
>
> { prefix }/CollectController/**
>
> 针对：视频



#### insert发起收藏接口

path：{ ServerPath }/lemonbily/CollectController/insert

method： POST

produces：application/json;charset=UTF-8

body（JSON）：

```json
  {
            "colid": 1000, 	#发起收藏用户的ID
            "colpid": null,	#主键
            "vid": 5		#收藏的剧集ID
        }
```

##### 正常回包

```Json
{
    "msg": "正常回包",
    "code": 0,
    "data":  {
            "colid": 1000,
            "colpid": 1,
            "vid": 5
        }
}
```

##### 出现错误的回包

```json
{
    "msg": "数据库插入错误", 	
    "code": 2					
}
{
    "msg": "接收的UID是非法ID", 	
    "code": 1401					
}
{
    "msg": "接收到的对象为空", 	
    "code": 2					
}
```



#### deleteByID根据主键ID删除

path：{ ServerPath }/lemonbily/CollectController/selectByColPID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| colPID | 收藏表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "删除的ID是非法ID",
    "code": 4
}
```



#### deleteByColIDAndVID根据ColID和VID删除数据

path：{ ServerPath }/lemonbily/CollectController/deleteByColIDAndVID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                    |
| ------ | :-------------------------------------- |
| colID  | 执行收藏操作的用户ID，基于LoginID的外键 |
| vid    | 收藏的视频ID。基于Video表的外键         |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0  
}
```



##### 失败回包

```json
{
    "msg": "数据库删除错误",
    "code": 4
}
{
    "msg": "接收的ColID是非法ID",
    "code": 4
}
{
    "msg": "接收的VID是非法ID",
    "code": 1701
}
```



#### selectAll

path：{ ServerPath }/lemonbily/CollectController/selectAll

method： GET

produces：application/json;charset=UTF-8

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "colid": 1000,
            "colpid": 1,
            "vid": 5
        },
        {
            "colid": 1001,
            "colpid": 2,
            "vid": 1
        },
        {
            "colid": 1000,
            "colpid": 3,
            "vid": 2
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByID根据主键获取数据

path：{ ServerPath }/lemonbily/CollectController/selectByColPID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                      |
| ------ | :------------------------ |
| colPID | 收藏表被分配的id,作为主键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data":    },
        {
            "colid": 1001,
            "colpid": 2,
            "vid": 1
        }
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByColID根据用户ID获取

path：{ ServerPath }/lemonbily/CollectController/selectByColID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                    |
| ------ | :-------------------------------------- |
| colID  | 执行收藏操作的用户ID，基于LoginID的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "colid": 1001,
            "colpid": 2,
            "vid": 1
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByVID根据视频ID获取

path：{ ServerPath }/lemonbily/CollectController/selectByVID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                            |
| ------ | :------------------------------ |
| vid    | 收藏的视频ID。基于Video表的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "colid": 1001,
            "colpid": 2,
            "vid": 1
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
```



#### selectByColIDAndVID根据用户ID和视频ID

path：{ ServerPath }/lemonbily/CollectController/selectByColIDAndVID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                                    |
| ------ | :-------------------------------------- |
| colID  | 执行收藏操作的用户ID，基于LoginID的外键 |
| vid    | 收藏的视频ID。基于Video表的外键         |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": [
        {
            "colid": 1001,
            "colpid": 2,
            "vid": 1
        }
    ]
}
```

##### 失败回包

```json
{
    "msg": "接收的ColID是非法ID",
    "code": 1
}
{
    "msg": "查找的表单为空",
    "code": 1
}
{
    "msg": "接收的VID是非法ID",
    "code": 1
}
```



#### selectCollectNumberByVID获取某视频的被收藏数量

path：{ ServerPath }/lemonbily/CollectController/selectCollectNumberByVID

method： POST

produces：application/json;charset=UTF-8

body（ from表单 ）：

| 字段名 | 描述                            |
| ------ | :------------------------------ |
| vid    | 收藏的视频ID。基于Video表的外键 |

##### 成功回包

```json
{
    "msg": "正常回包",
    "code": 0,
    "data": 1 	#被收藏数量
}
```

##### 失败回包

```json
{
    "msg": "查找的ID是非法ID",
    "code": 1
}
```



### 状态码

```java
/**
     * 收藏操作相关状态码
     * 状态码范围：1701-1800
     */

    public static final int LEMONBILY_COLLECT_ILLEGAL_CODE = 1701;
    public static final String LEMONBILY_COLLECT_USERID_ILLEGAL_CONTENT = "接收的ColID是非法ID";
    public static final String LEMONBILY_COLLECT_VID_ILLEGAL_CONTENT = "接收的VID是非法ID";

```

