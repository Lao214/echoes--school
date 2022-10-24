package com.example.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.base.exceptionhandler.EchoesException;
import com.example.eduService.entity.EduChapter;
import com.example.eduService.dao.EduChapterMapper;
import com.example.eduService.entity.EduVideo;
import com.example.eduService.entity.chapter.ChapterVo;
import com.example.eduService.entity.chapter.VideoVo;
import com.example.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduService.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService videoService;
    /**
     * @description: 课程大纲列表，根据id进行查询
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/9/30 6:47 PM
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters= baseMapper.selectList(chapterQueryWrapper);

        //根据课程id查询课程里面的所有小节
        QueryWrapper<EduVideo> wrapperVideo =new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideos= videoService.list(wrapperVideo);

        //遍历查询章节list集合进行封装
        List<ChapterVo> finalList= new ArrayList<>();
        //遍历查询章节的list集合
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo =new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);


            List<VideoVo> videoVos =new ArrayList<>();
            for (int i = 0; i <eduVideos.size() ; i++) {
               EduVideo eduVideo =  eduVideos.get(i);
               if (eduVideo.getChapterId().equals(eduChapter.getId())){
                   VideoVo videoVo =new VideoVo();
                   BeanUtils.copyProperties(eduVideo,videoVo);
                   videoVos.add(videoVo);
               }
            }
            chapterVo.setChildren(videoVos);
        }

        return finalList;
    }

    /**
     * @description: 删除章节的方法
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/2 4:09 PM
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //查询该章节是否有小节，如果有，不删除
        QueryWrapper<EduVideo> eduVideoQueryWrapper=new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        int count=videoService.count(eduVideoQueryWrapper);
        if(count>0){
            throw new EchoesException(201,"该章节还有小节，不可删除");
        }else {
            //没数据，进行删除
          int result=  baseMapper.deleteById(chapterId);
          return result>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
