package com.example.orderService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.orderService.entity.TOrder;
import com.example.orderService.service.TOrderService;
import com.example.utils.JwtUtils;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/eduOrder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    /**
     * @description: 生成订单
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/14 7:11 PM
     */
    @GetMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单 生成订单号
        String orderNo = orderService.createOrders(courseId,memberId);
        return  Result.success().data("orderNo",orderNo);
    }

    /**
     * @description: 根据订单id查询订单的信息
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/14 8:19 PM
     */
    @GetMapping("getOrderInfo/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<TOrder> wrapper =new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = orderService.getOne(wrapper);
        return Result.success().data("item",order);
    }

    /**
     * @description: 根据课程id和用户id查询订单表中订单状态
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/17 5:09 PM
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<TOrder> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }


}

