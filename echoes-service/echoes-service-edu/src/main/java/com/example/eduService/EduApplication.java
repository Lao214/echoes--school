package com.example.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 劳威锟
 * @version 1.0
 * @description: TODO
 * @date 2022/9/27 9:17 AM
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@EnableDiscoveryClient  //nacos注册
@EnableFeignClients     //服务调用
public class EduApplication  {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
