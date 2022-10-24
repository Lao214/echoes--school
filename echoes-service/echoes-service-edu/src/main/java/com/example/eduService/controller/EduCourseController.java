package com.example.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.entity.EduCourse;
import com.example.eduService.entity.EduTeacher;
import com.example.eduService.entity.vo.CourseInfoVo;
import com.example.eduService.entity.vo.CoursePublishVo;
import com.example.eduService.entity.vo.CourseQuery;
import com.example.eduService.entity.vo.TeacherQuery;
import com.example.eduService.service.EduCourseService;
import com.example.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/eduService/edu-course")

public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);
        //返回添加之后的课程id，为了后面添加大纲使用
        return Result.success().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo= courseService.getCourseInfo(courseId);
        return Result.success().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
          courseService.updateCourseInfo(courseInfoVo);
        return Result.success();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id){
      CoursePublishVo coursePublishVo= courseService.getPublishCourseInfo(id);
      return  Result.success().data("coursePublishVo",coursePublishVo);
    }

    //发布课程
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id){
        EduCourse eduCourse =new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return  Result.success();
    }

    //分页条件查询课程
    @ApiOperation(value = "根据条件分页查询list")
    @PostMapping("queryCourseListPage/{current}/{limit}")
    public  Result queryCourseListPage(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) CourseQuery courseQuery){
        /**创建page对象**/
        Page<EduCourse> eduCoursePage =new Page<>(current,limit);
        /**构建条件**/
        QueryWrapper<EduCourse> queryWrapper =new QueryWrapper<>();
        String  title    = courseQuery.getTitle();
        String  teacher  = courseQuery.getTeacher();
        String  status   = courseQuery.getStatus();
        String  begin    = courseQuery.getBegin();
        String  end      = courseQuery.getEnd();
        /**判断是否为空，如果为空，不拼接条件**/
        if(!StringUtils.isEmpty(title)){
            //构建条件
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            //构建条件
            queryWrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(teacher)){
            //构建条件
            queryWrapper.like("teacher",teacher);
        }
        if(!StringUtils.isEmpty(begin)){
            //构建条件
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            //构建条件
            queryWrapper.le("gmt_create",end);
        }
        /**排序 根据创建时间降序**/
        queryWrapper.orderByDesc("gmt_create");
        /**调用分页方法**/
        courseService.page(eduCoursePage,queryWrapper);
        /**total 为所有记录**/
        long total =eduCoursePage.getTotal();
        List<EduCourse> list=eduCoursePage.getRecords();
        return  Result.success().data("rows",list).data("total",total);
    }

    /**删除课程**/
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return Result.success();
    }
}

