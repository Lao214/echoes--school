package com.example.base.exceptionhandler;

import com.example.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/27 2:02 PM
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /** 
     * @description: 统一异常处理
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/27 2:04 PM
     */ 
    @ExceptionHandler(Exception.class)
    @ResponseBody   //为了能够返回数据
    public Result error(Exception e){
         e.printStackTrace();
         return  Result.error().msg("全局异常处理");
    }


    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody   //为了能够返回数据
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return  Result.error().msg("执行了ArithmeticException特定异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(EchoesException.class)
    @ResponseBody   //为了能够返回数据
    public Result error(EchoesException e){
        e.printStackTrace();
        return  Result.error().code(e.getCode()).msg(e.getMsg());
    }


}
