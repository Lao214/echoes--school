package com.example.eduService.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.client.OrderClient;
import com.example.eduService.entity.EduCourse;
import com.example.eduService.entity.chapter.ChapterVo;
import com.example.eduService.entity.frontvo.CourseFrontVo;
import com.example.eduService.entity.frontvo.CourseWebVo;
import com.example.eduService.service.EduChapterService;
import com.example.eduService.service.EduCourseService;
import com.example.eduService.service.EduTeacherService;
import com.example.utils.JwtUtils;
import com.example.utils.Result;
import com.example.vo.CourseOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/12 6:40 PM
 */
@RestController
@RequestMapping("/eduService/frontCourse")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    //条件查询带分页
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse>  eduCoursePage = new Page<>(page,limit);
        Map<String,Object> map=eduCourseService.getCourseFrontList(eduCoursePage,courseFrontVo);
        return Result.success().data(map);
    }

    //课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public  Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，查询一个sql语句查询课程信息

        CourseWebVo courseWebVo= eduCourseService.getBaseCourseInfo(courseId);
                //根据课程ID查询章节和小节
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id，查询该课程是否已经购买
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = orderClient.isBuyCourse(courseId, memberIdByJwtToken);
        return Result.success().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList).data("isBuy",buyCourse);
    }


    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{courseId}")
    public CourseOrder getCourseInfoOrder(@PathVariable String courseId){
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(courseId);
        CourseOrder courseOrder =new CourseOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseOrder);
        return  courseOrder;
    }
}
