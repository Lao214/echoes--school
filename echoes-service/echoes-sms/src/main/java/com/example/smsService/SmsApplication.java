package com.example.smsService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/8 4:26 PM
 */

@SpringBootApplication//不去自动加载数据库的信息
@EnableDiscoveryClient  //nacos注册
@EnableFeignClients     //服务调用
@MapperScan("com.example.smsService.dao")
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class,args);
    }
}



