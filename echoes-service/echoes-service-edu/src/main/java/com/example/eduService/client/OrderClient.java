package com.example.eduService.client;


import com.example.vo.CourseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-order",fallback = OrderClientImpl.class)//当请求容错 会运行fallback的类的方法
@Component
public interface OrderClient {

    @GetMapping("/eduOrder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId);

}
