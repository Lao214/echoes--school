package com.example.orderService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/13 10:46 AM
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@MapperScan("com.example.orderService.dao")
@EnableDiscoveryClient  //nacos注册
@EnableFeignClients     //服务调用
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
