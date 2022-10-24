package com.example.countService.client;


import com.example.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/eduUCenter/uCenter-member/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);
}
