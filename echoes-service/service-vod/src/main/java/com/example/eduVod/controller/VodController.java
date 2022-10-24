package com.example.eduVod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.example.base.exceptionhandler.EchoesException;
import com.example.eduVod.Utils.ConstantPropertiesUtils;
import com.example.eduVod.Utils.InitObject;
import com.example.eduVod.service.VodService;
import com.example.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/3 5:48 PM
 */
@RestController
@RequestMapping("/eduVideo")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;



    /**
     * @description: 上传视频到阿里云
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/3 5:50 PM
     */
    @PostMapping("uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file){
        String videoId = vodService.uploadVideoToAliyun(file);
        return Result.success().data("videoId",videoId);
    }

    /**
     * @description: 根据视频id删除阿里云视频
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/4 4:28 PM
     */
    @DeleteMapping("removeAliyunvod/{id}")
    public Result removeAliyunvod(@PathVariable String id){
        try {
         //初始化对象
         DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.KEY_ID,ConstantPropertiesUtils.KEY_SECRET);
         //创建删除视频的request对象
         DeleteVideoRequest request =new DeleteVideoRequest();
         //向request 设置视频id
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new EchoesException(201,"删除失败");
        }
    }

    /**
     * @description: 批量删除阿里云视频
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/5 3:01 PM
     */
    @DeleteMapping("deleteBatch")
    public  Result deleteBatch(@RequestParam("videoList") List<String> videoList){
        try {
            //初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.KEY_ID,ConstantPropertiesUtils.KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request =new DeleteVideoRequest();
            //向request 设置视频id
            request.setVideoIds(StringUtils.join(videoList.toArray(),","));
            client.getAcsResponse(request);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new EchoesException(201,"删除失败");
        }
    }
    
    /** 
     * @description: 根据视频id获取视频播放凭证 
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/10/13 9:29 AM
     */
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request =new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法获取凭证
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            return Result.success().data("playAuth",acsResponse.getPlayAuth());
        }catch (Exception e){
            throw new EchoesException(201,"获取视频凭证失败");
        }
    }

}
