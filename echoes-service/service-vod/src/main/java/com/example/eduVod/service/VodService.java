package com.example.eduVod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/3 5:49 PM
 */
public interface VodService {
    String uploadVideoToAliyun(MultipartFile file);
}
