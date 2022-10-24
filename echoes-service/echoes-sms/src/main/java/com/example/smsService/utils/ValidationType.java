package com.example.smsService.utils;

public enum ValidationType {
    LOGIN(1),
    FORGET_PASS(2),
    REGISTER(2);

    private  Integer code;

    public  Integer getCode(){
        return  code;
    }
    ValidationType(Integer code) {
        this.code = code;
    }
}
