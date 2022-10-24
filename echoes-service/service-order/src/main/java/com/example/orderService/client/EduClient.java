package com.example.orderService.client;

import com.example.vo.CourseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("echoes-service-edu")
public interface EduClient {

    @PostMapping("/eduService/frontCourse/getCourseInfoOrder/{courseId}")
    public CourseOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);

}
