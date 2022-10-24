package com.example.eduService.service.impl;

import com.example.eduService.entity.EduCourseDescription;
import com.example.eduService.dao.EduCourseDescriptionMapper;
import com.example.eduService.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeDescriptionByCourseId(String courseId) {

    }
}
