package com.example.eduService.controller;

import com.example.utils.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/28 9:25 AM
 */

@RestController
@RequestMapping("/eduService/edu-login")
public class EduLoginController {

    
    /** 
     * @description: login 
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/28 9:26 AM
     */ 
    @PostMapping("login")
    public Result login(){
        return  Result.success().data("token","admin");
    }
    
    /** 
     * @description: 获取用户信息 
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/28 9:27 AM
     */
    @GetMapping("info")
    public Result info(){
        return  Result.success().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
