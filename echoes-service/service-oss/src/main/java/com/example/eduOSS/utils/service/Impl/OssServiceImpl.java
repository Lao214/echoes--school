package com.example.eduOSS.utils.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.eduOSS.utils.ConstantPropertiesUtils;
import com.example.eduOSS.utils.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author 劳威锟
 * @version 1.0
 * @description: TODO
 * @date 2022/9/29 8:44 AM
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint= ConstantPropertiesUtils.END_POIND;

        String accessKeyId=ConstantPropertiesUtils.KEY_ID;

        String accessKeySecret=ConstantPropertiesUtils.KEY_SECRET;

        String bucketName =ConstantPropertiesUtils.BUCKET_NAME;

        try{
            //创建OSS上传实例
            OSS ossClient= new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            //文件名唯一化
            String uuid=UUID.randomUUID().toString().replaceAll("-","");
            //获取文件名称
            String fileName =uuid+file.getOriginalFilename();
            //根据日期创建文件夹
            String dateStr=  new DateTime().toString("yyyy/MM/dd");
            //上传
            ossClient.putObject(bucketName,dateStr+"/"+fileName,inputStream);
            //关闭OSSClient
            ossClient.shutdown();
            //拼接返回路径
            fileName = dateStr+"/"+fileName;
            String path ="https://"+bucketName+"."+endpoint+"/"+fileName;
            return path;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
