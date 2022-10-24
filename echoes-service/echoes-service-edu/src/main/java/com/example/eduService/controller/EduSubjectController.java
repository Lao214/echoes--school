package com.example.eduService.controller;


import com.example.eduService.entity.vo.OneSubject;
import com.example.eduService.service.EduSubjectService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/eduService/edu-subject")

public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    /**
     * @description: 添加课程分类
     * @param: file
     * @return:
     * @author 劳威锟
     * @date: 2022/9/29 1:53 PM
     */
    @PostMapping("uploadSubject")
    public Result uploadSubject(MultipartFile file){
        //上传excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return Result.success();
    }
    
    
    /** 
     * @description: 以树形数据结构查询课程列表
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/29 4:21 PM
     */ 
    @GetMapping("getSubjectTree")
    public  Result getSubjectTree(){
        List<OneSubject> list= eduSubjectService.getAllOneTwoSubject();
        return  Result.success().data("list",list);
    }
}

