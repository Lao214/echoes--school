package com.example.eduService.controller;


import com.example.base.exceptionhandler.EchoesException;
import com.example.eduService.client.VodClient;
import com.example.eduService.entity.EduVideo;
import com.example.eduService.service.EduVideoService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/eduService/edu-video")

public class EduVideoController {


    @Autowired
    private EduVideoService videoService;

    /**服务调用**/
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return Result.success();
    }


    //删除小节
    //TODO 后面这个方法需要完善：删除小节的时候，要删除里面的视频
    @DeleteMapping("deleteVideo/{id}")
    public Result deleteVideo(@PathVariable String id){
        //根据小节id获取视频id，再调用方法实现视频删除
        EduVideo eduVideo= videoService.getById(id);
        //判断视频id是否为空
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            Result result = vodClient.removeAliyunvod(eduVideo.getVideoSourceId());
            if(result.getCode()==201){
                throw  new EchoesException(201,"删除视频失败，熔断器......");
            }
        }
        /**删除小节（细节：一定要先删视频再删小节，不然视频id就没了）**/
        videoService.removeById(id);
        return Result.success();
    }

    //修改小节
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return Result.success();
    }

}

