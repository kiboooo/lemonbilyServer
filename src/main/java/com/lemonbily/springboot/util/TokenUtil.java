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

    public static void generateTokenAddMap(Object object) {
        if (object == null) {
            return;
        }
        switch (object.getClass().getName()) {
            case "com.lemonbily.springboot.entity.Login": {
                Login login = (Login) object;
                if (tokenMap.containsKey(login.getId().toString())){
                    tokenMap.replace(login.getId().toString(),
                            generateLoginUserToken(login));
                }else {
                    tokenMap.put(
                            login.getId().toString(),
                            generateLoginUserToken(login)
                    );
                }
                break;
            }
            default:
                break;
        }
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

    public static Token generateLoginUserToken(Login login) {
        String token = login.getId()
                + login.getLpassword()
                + login.getName()
                + login.getLphone()
                + SALT;

        return new Token(DigestUtils.md5Hex(token), login.getLlivetime());
    }

    private static boolean isTokenSurvive(Date limit) {
        return (System.currentTimeMillis() - limit.getTime()) < CommonBean.liveTimeLimit;
    }

}
