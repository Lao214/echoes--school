package com.example.eduOSS.utils.controller;

import com.example.eduOSS.utils.service.OssService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/29 8:45 AM
 */
@RestController
@RequestMapping("/eduOSS")
@CrossOrigin
public class OSSController {

    @Autowired
    private OssService ossService;

    //上传头像
    @PostMapping("uploadOSSFile")
    public Result uploadOSSFile(MultipartFile file){
        //获取上传文件 MultipartFile
        String url=   ossService.uploadFileAvatar(file);
        return Result.success().data("url",url);
    }
}
