package com.example.eduService.service;

import com.example.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
