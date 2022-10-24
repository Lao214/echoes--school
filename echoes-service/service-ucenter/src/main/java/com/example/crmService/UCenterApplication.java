package com.example.crmService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/9 10:16 AM
 */
@SpringBootApplication //不加载数据库信息
@ComponentScan(basePackages = {"com.example"})
@MapperScan("com.example.crmService.dao")
@EnableDiscoveryClient
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }
}
