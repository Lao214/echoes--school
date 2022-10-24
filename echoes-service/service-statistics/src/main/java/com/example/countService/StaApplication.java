package com.example.countService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/17 7:06 PM
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@MapperScan("com.example.countService.dao")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
