package com.lemonbily.springboot;

import org.apache.commons.codec.digest.DigestUtils;

public class test {
    public static void main(String[] args) {

        System.out.println(System.currentTimeMillis());
        System.out.println(DigestUtils.md5Hex("admin")
        );
    }
}
