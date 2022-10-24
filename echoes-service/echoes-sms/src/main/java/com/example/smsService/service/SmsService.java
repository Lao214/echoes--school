package com.example.smsService.service;

import com.example.utils.Result;

import java.util.Map;

public interface SmsService {
    boolean send(Map<String, Object> param, String phone);

    Result sendEmail(String email, Integer type);

    Result sendEmailCacheRedis(String email, Integer type);
}
