package com.example.orderService.service;

import com.example.orderService.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-14
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String memberId);
}
