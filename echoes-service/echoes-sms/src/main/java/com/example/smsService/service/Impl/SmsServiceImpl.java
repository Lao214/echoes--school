package com.example.smsService.service.Impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.smsService.entity.Validation;
import com.example.smsService.service.SmsService;
import com.example.smsService.service.ValidationService;
import com.example.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/8 4:29 PM
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String from;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return  false;

        DefaultProfile profile =
             DefaultProfile.getProfile("default","LTAIQ6nPY","FQ7");
        IAcsClient client= new DefaultAcsClient(profile);

        //设置相关参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-05");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone);      //手机号
        request.putQueryParameter("signName","echoes"); //阿里云签名名称
        request.putQueryParameter("TemplateCode","SMS_254300949");//阿里云模板CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        //最终的发送
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public Result sendEmail(String email, Integer type) {
            Date now =new Date();
            //先查询同类型code
            QueryWrapper<Validation> validationQueryWrapper= new QueryWrapper<>();
            validationQueryWrapper.eq("email",email);
            validationQueryWrapper.eq("type",type);
            validationQueryWrapper.ge("time",now);   //查询数据库没过期的code
            Validation validationExp=  validationService.getOne(validationQueryWrapper);
            if(validationExp!=null){
                return Result.error().code(201).msg("当前您的验证码依然有效，请不要发送");
            }
            //删除 该email的code
            UpdateWrapper<Validation> validationUpdateWrapper=new UpdateWrapper<>();
            validationUpdateWrapper.eq("email",email);
            validationUpdateWrapper.eq("type",type);
            validationService.remove(validationUpdateWrapper);
            //先删再插入
            SimpleMailMessage message =new SimpleMailMessage();
            message.setFrom(from);
            message.setSubject("【echoes】邮箱验证");
            String code= RandomUtil.randomNumbers(4);
            message.setText("您本次邮箱验证码为："+code+",请妥善保管，切勿泄露，有效期为5分钟");
            message.setTo(email);
            message.setSentDate(now);
//              message.setCc("抄送人");
//              message.setBcc("密送人");
            javaMailSender.send(message);
            /**发送之后，保存code**/
            Validation validation =new Validation();
            validation.setCode(code);
            validation.setEmail(email);
            validation.setType(type);                                 //1为注册邮箱验证 2为登录邮箱验证
            validation.setTime(DateUtil.offsetMinute(now,5));  //现在往后推5分钟
            validationService.save(validation);
            return Result.success().code(200).msg("发送成功");
        }

    @Override
    public Result sendEmailCacheRedis(String email, Integer type) {
        Date now =new Date();
        //从redis中获取验证码，如果获取到直接返回
        String code =  redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)){
            return  Result.success().msg("当前您的验证码依然有效，请不要发送");
        }
        //先删再插入
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject("【echoes】邮箱验证");
        code= RandomUtil.randomNumbers(4);
        message.setText("您本次邮箱验证码为："+code+",请妥善保管，切勿泄露，有效期为5分钟");
        message.setTo(email);
        message.setSentDate(now);
//         message.setCc("抄送人");
//         message.setBcc("密送人");
        try {
            javaMailSender.send(message);
            //如果发送成功，把验证码放到redis中,设置为5分钟过时
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return Result.success().msg("发送成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().msg("发送失败");
        }

    }

}
