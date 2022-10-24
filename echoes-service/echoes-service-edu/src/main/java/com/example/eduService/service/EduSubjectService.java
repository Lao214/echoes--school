package com.example.eduService.service;

import com.example.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduService.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
