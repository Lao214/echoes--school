package com.example.eduService.controller;


import com.example.eduService.entity.EduChapter;
import com.example.eduService.entity.chapter.ChapterVo;
import com.example.eduService.service.EduChapterService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/eduService/edu-chapter")

public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list= chapterService.getChapterVideoByCourseId(courseId);
        return  Result.success().data("allChapterVideo",list);
    }

    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return  Result.success();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId){
         EduChapter eduChapter=   chapterService.getById(chapterId);
        return  Result.success().data("chapter",eduChapter);
    }

    //修改
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return  Result.success();
    }


    //删除
    @DeleteMapping("{chapterId}")
    public Result deleteChapterInfo(@PathVariable String chapterId){
       boolean flag=  chapterService.deleteChapter(chapterId);
        if(flag){
            return  Result.success();
        }else {
            return Result.error();
        }
    }


}

