package com.example.eduService.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.entity.EduCourse;
import com.example.eduService.entity.EduTeacher;
import com.example.eduService.service.EduCourseService;
import com.example.eduService.service.EduTeacherService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/11 9:42 AM
 */
@CrossOrigin
@RestController
@RequestMapping("/eduService/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> eduTeacherPage =new Page<>(page,limit);
        Map<String,Object> map =eduTeacherService.getTeacherFrontList(eduTeacherPage);
        //返回分页中的所有数据
        return Result.success().data(map);
    }

    //讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        //1.根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        //2.根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);

        return  Result.success().data("teacher",eduTeacher).data("courseList",list);
    }
}
