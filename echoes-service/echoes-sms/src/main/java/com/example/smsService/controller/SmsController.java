package com.example.smsService.controller;

import com.example.smsService.service.SmsService;
import com.example.smsService.utils.RandomUtil;
import com.example.smsService.utils.ValidationType;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/8 4:28 PM
 */
@RestController
@RequestMapping("/eduSms")
@CrossOrigin
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("send/{phone}")
    public Result sendSms(@PathVariable String phone){
        //从redis中获取验证码，如果获取到直接返回
        String code =  redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return  Result.success();
        }
        //如果redis中获取不到，进行阿里云发送
        //生成随机六位，传递给阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service中调用发送短信的方法
        boolean isSend =smsService.send(param,phone);
        if(isSend){
            //如果发送成功，把验证码放到redis中,设置为5分钟过时
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return  Result.success();
        }else {
            return  Result.error().msg("短信发送失败");
        }

    }

    /**
     * @description:
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/9 9:08 AM
     */
    @GetMapping(value = "sendEmailForLogin/{email}")
    public  Result sendEmail(@PathVariable String email){
        Integer type= ValidationType.LOGIN.getCode();
        Result result= smsService.sendEmail(email,type);
        return  result;
    }

    /**
     * @description:
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/9 9:08 AM
     */
    @GetMapping(value = "sendEmailForRegister/{email}")
    public  Result sendEmailForRegister(@PathVariable String email){
        Integer type= ValidationType.REGISTER.getCode();
        Result result= smsService.sendEmailCacheRedis(email,type);
        return  result;
    }



}
