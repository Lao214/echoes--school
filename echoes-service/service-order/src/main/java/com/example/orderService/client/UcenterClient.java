package com.example.orderService.client;

import com.example.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/14 7:31 PM
 */

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/eduUCenter/uCenter-member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
