package com.example.orderService.controller;


import com.example.orderService.service.TPayLogService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/eduOrder/pay")
@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    //生成微信支付二维码接口
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        //返回相关信息，包含二维码的地址及其他信息
        Map map =payLogService.createNative(orderNo);
        return Result.success().data(map);
    }


    /**
     * @description: 根据订单号查询支付状态
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/15 4:45 PM
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo){
       Map<String,String> map = payLogService.queryPayStatus(orderNo);
       if(map==null){
           return  Result.error().msg("支付出错了");
       }
       if(map.get("trade_state").equals("SUCCESS")){
           //支付成功，可以更新订单表的状态,支付表添加一条记录
           payLogService.updateOrdersStatus(map);
           return Result.success().msg("支付成功");
       }
       return Result.success().code(25000).msg("支付中");
    }

}

