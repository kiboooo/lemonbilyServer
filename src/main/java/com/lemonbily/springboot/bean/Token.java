package com.lemonbily.springboot.bean;

import java.util.Date;

public class Token {
    private String token;
    private Date liveTimeLimit;

    public Token() {
    }

    public Token(String token, Date liveTimeLimit) {
        this.token = token;
        this.liveTimeLimit = liveTimeLimit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLiveTimeLimit() {
        return liveTimeLimit;
    }

    public void setLiveTimeLimit(Date liveTimeLimit) {
        this.liveTimeLimit = liveTimeLimit;
    }
}
