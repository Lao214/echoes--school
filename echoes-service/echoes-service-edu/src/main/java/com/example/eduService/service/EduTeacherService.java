package com.example.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-27
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> eduTeacherPage);
}
