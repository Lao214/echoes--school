package com.example.eduVod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/3 5:44 PM
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//不加载数据库信息
@ComponentScan(basePackages = {"com.example"})
@EnableDiscoveryClient
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }
}


