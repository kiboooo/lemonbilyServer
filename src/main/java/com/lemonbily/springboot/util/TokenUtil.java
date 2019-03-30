package com.lemonbily.springboot.util;

import com.lemonbily.springboot.entity.Login;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenUtil {

    private static final String SALT = "lemon";


    //存储了所有用户的Token
    private static Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public static void generateToken(Object object) {
        if (object == null) {
            return;
        }
        switch (object.getClass().getName()) {
            case "com.lemonbily.springboot.entity.Login": {
                Login login = (Login) object;
                tokenMap.put(
                        login.getId().toString(),
                        generateLoginUserToken(login)
                );
                break;
            }
            default:
                break;
        }
    }

    public static boolean isTokenEffective(String idKey,String token) {
        String mToken = tokenMap.get(idKey);
        if (mToken == null) {
            return false;
        }
        return mToken.equals(token);
    }

    public static void updateToken(String idKey, String token) {
        tokenMap.replace(idKey, token);
    }

    public static void removeToken(String idKey) {
        tokenMap.remove(idKey);
    }

    private static String generateLoginUserToken(Login login) {
        String token = login.getId()
                + login.getLpassword()
                + login.getName()
                + login.getLphone()
                + SALT;
        return DigestUtils.md5Hex(token);
    }

}
