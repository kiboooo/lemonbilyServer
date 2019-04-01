package com.lemonbily.springboot.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    private static final String RESPONSE_CODE = "code";
    private static final String RESPONSE_TOKEN = "token";
    private static final String RESPONSE_DATA = "data";


    public static JSONObject generateJsonResponse(int code, Object data) {
        JSONObject object = new JSONObject();
        object.put(RESPONSE_CODE, code);
        object.put(RESPONSE_DATA, data);
        return object;
    }

    public static JSONObject generateJsonResponse(int code, String token,Object data) {
        JSONObject object = new JSONObject();
        object.put(RESPONSE_CODE, code);
        object.put(RESPONSE_TOKEN, token);
        object.put(RESPONSE_DATA, data);
        return object;
    }
}
