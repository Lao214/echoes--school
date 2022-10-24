package com.example.eduVod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 劳威锟
 * @version 1.0
 * @description: TODO  InitializingBean初始化的时候，进行操作，执行方法。
 * @date 2022/9/29 8:35 AM
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置文件内容

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;



    //定义公开静态的常量

    public  static  String KEY_ID;
    public  static  String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=keyId;
        KEY_SECRET=keySecret;
    }
}
