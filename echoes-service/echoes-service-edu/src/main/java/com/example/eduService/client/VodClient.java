package com.example.eduService.client;

import com.example.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "service-vod",fallback = VodClientImpl.class)//当请求容错 会运行fallback的类的方法
@Component
public interface VodClient {

    //定义调用的方法路径
    @DeleteMapping("/eduVideo/removeAliyunvod/{id}")
    public Result removeAliyunvod(@PathVariable("id") String id);

    @DeleteMapping("/eduVideo/deleteBatch")
    public  Result deleteBatch(@RequestParam("videoList") List<String> videoList);
}
