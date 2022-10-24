package com.example.eduService.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduService.entity.EduCourse;
import com.example.eduService.entity.EduTeacher;
import com.example.eduService.service.EduCourseService;
import com.example.eduService.service.EduTeacherService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/8 8:44 AM
 */
@RestController
@RequestMapping("/eduService/front")
@CrossOrigin
public class FrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前八条热门课程，查询前四名师
    @GetMapping("indexFront")
    @Cacheable(value = "banner",key = "'selectFront'")
    public Result indexFront(){
        //查询八门课程
        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList= eduCourseService.list(wrapper);

        //查询四名讲师
        QueryWrapper<EduTeacher> wrapper2 =new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 4");
        eduTeacherService.list(wrapper2);
        List<EduTeacher> teacherList= eduTeacherService.list(wrapper2);

        return Result.success().data("courseList",courseList).data("teacherList",teacherList);
    }
}
