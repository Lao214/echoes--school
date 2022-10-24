package com.example.base.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/27 5:04 PM
 */
@Data
@AllArgsConstructor  //生成有参数的构造方法
@NoArgsConstructor   //生成无参的构造方法
public class EchoesException extends RuntimeException{


    private  Integer code;  //状态码

    private  String msg;    //异常信息
}
