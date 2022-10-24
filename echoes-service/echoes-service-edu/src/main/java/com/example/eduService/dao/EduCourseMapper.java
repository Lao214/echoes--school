package com.example.eduService.dao;

import com.example.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eduService.entity.frontvo.CourseWebVo;
import com.example.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {


    public CoursePublishVo getPublishInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
