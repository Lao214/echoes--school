package com.example.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.base.exceptionhandler.EchoesException;
import com.example.eduService.client.VodClient;
import com.example.eduService.entity.EduVideo;
import com.example.eduService.dao.EduVideoMapper;
import com.example.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //TODO 删除小节，删除对应的视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询课程所有的视频id
        QueryWrapper<EduVideo> wrapperVideo =new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        List<String> videoIds=new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        if(videoIds.size()>0){
            Result result = vodClient.deleteBatch(videoIds);
            if(result.getCode()==201){
                throw new EchoesException(201,"批量删除失败，熔断器......");
            }
        }
        QueryWrapper<EduVideo> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
