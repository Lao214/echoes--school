package com.example.orderService.service.impl;

import com.example.orderService.client.EduClient;
import com.example.orderService.client.UcenterClient;
import com.example.orderService.entity.TOrder;
import com.example.orderService.dao.TOrderMapper;
import com.example.orderService.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.orderService.utils.OrderNoUtil;
import com.example.vo.CourseOrder;
import com.example.vo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-14
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {


    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * @description: 生成订单的接口
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/14 7:15 PM
     */
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据会员id获取到用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //通过远程调用根据课程id获取到课程信息
        CourseOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        //创建对象
        TOrder order =new TOrder();
        /**订单号**/
        order.setOrderNo(OrderNoUtil.getOrderNo());
        /**课程id**/
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTotalFee(courseInfoOrder.getPrice());

        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        //支付状态 0：未支付 1：支付
        order.setStatus(0);
        //支付类型 1：微信
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
    
    

}
