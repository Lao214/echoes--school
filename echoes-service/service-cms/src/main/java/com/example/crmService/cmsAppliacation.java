package com.example.crmService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/7 12:54 PM
 */
@SpringBootApplication
@ComponentScan({"com.example"})
@MapperScan("com.example.crmService.dao")
public class cmsAppliacation {

    public static void main(String[] args) {
        SpringApplication.run(cmsAppliacation.class,args);
    }
}
