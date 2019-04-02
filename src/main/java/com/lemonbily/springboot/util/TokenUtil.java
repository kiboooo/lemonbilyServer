package com.lemonbily.springboot.util;

import com.lemonbily.springboot.bean.CommonBean;
import com.lemonbily.springboot.bean.Token;
import com.lemonbily.springboot.controller.LoginController;
import com.lemonbily.springboot.entity.Login;
import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenUtil {

    private static final String SALT = "lemon";

    //存储了所有用户的Token
    private static Map<String, Token> tokenMap = new ConcurrentHashMap<>();

    public static String generateTokenAddMap(Object object) {
        Token mToken = new Token() ;
        if (object == null) {
            return "";
        }
        switch (object.getClass().getName()) {
            case "com.lemonbily.springboot.entity.Login": {
                Login login = (Login) object;
                mToken = generateLoginUserToken(login);
                if (tokenMap.containsKey(login.getId().toString())){
                    tokenMap.replace(login.getId().toString(),
                            mToken);
                }else {
                    tokenMap.put(
                            login.getId().toString(),
                            mToken
                    );
                }
                break;
            }
            default:
                break;
        }
        return mToken.getToken();
    }

    public static boolean isTokenEffective(String idKey,String token) {
        Token mToken = tokenMap.get(idKey);
        return mToken != null
                && isTokenSurvive(mToken.getLiveTimeLimit())
                && mToken.getToken().equals(token);
    }

    public static void updateToken(Login login) {
        if(null == login) {
            return ;
        }
       tokenMap.replace(login.getId().toString(), generateLoginUserToken(login));
    }

    public static void removeToken(String idKey) {
        tokenMap.remove(idKey);
    }
    //TODO: 测试环境下获取测试用Token，上线环境下必须声明为 private
    public static Token generateLoginUserToken(Login login) {
        String token = login.getId()
                + login.getName()
                + login.getLphone()
                + SALT;

        return new Token(DigestUtils.md5Hex(token), login.getLlivetime());
    }

    private static boolean isTokenSurvive(Date limit) {
        return (System.currentTimeMillis() - limit.getTime()) < CommonBean.liveTimeLimit;
    }

}
