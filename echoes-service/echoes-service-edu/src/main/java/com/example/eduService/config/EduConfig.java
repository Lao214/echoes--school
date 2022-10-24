package com.example.eduService.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/27 9:20 AM
 */
@Configuration
@MapperScan("com.example.eduService.dao")
public class EduConfig {

    /** 
     * @description:  逻辑删除插件
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/27 11:14 AM
     */ 
    @Bean
    public ISqlInjector sqlInjector(){
        return  new LogicSqlInjector();
    }

    /**
     * @description: 分页插件
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/9/27 11:14 AM
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return  new PaginationInterceptor();
    }
}
