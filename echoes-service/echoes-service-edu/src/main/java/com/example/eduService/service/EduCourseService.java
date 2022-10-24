package com.example.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduService.entity.frontvo.CourseFrontVo;
import com.example.eduService.entity.frontvo.CourseWebVo;
import com.example.eduService.entity.vo.CourseInfoVo;
import com.example.eduService.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
