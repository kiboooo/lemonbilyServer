package com.lemonbily.springboot.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Login {
    private int  Id;
    private String Name;
    private String LPassWord;
    private String LPhone;
    private Timestamp LLiveTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLPassWord() {
        return LPassWord;
    }

    public void setLPassWord(String LPassWord) {
        this.LPassWord = LPassWord;
    }

    public String getLPhone() {
        return LPhone;
    }

    public void setLPhone(String LPhone) {
        this.LPhone = LPhone;
    }

    public Timestamp getLLiveTime() {
        return LLiveTime;
    }

    public void setLLiveTime(Timestamp LLiveTime) {
        this.LLiveTime = LLiveTime;
    }
}
