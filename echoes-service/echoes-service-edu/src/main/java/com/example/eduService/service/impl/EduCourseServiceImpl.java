package com.example.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.base.exceptionhandler.EchoesException;
import com.example.eduService.entity.EduCourse;
import com.example.eduService.dao.EduCourseMapper;
import com.example.eduService.entity.EduCourseDescription;
import com.example.eduService.entity.frontvo.CourseFrontVo;
import com.example.eduService.entity.frontvo.CourseWebVo;
import com.example.eduService.entity.vo.CourseInfoVo;
import com.example.eduService.entity.vo.CoursePublishVo;
import com.example.eduService.service.EduChapterService;
import com.example.eduService.service.EduCourseDescriptionService;
import com.example.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduService.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    /** 
     * @description: 添加课程基本信息的方法
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/29 8:29 PM
     */ 
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程基本信息
        EduCourse eduCourse =new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert=baseMapper.insert(eduCourse);
        if(insert==0){
            throw  new EchoesException(200,"添加课程信息失败");
        }

        String cid=eduCourse.getId();
        //向课程简介表添加课程简介您
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return  cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse= baseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription=courseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update=baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new EchoesException(201,"修改课程信息失败");
        }
        EduCourseDescription description=new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo coursePublishVo= baseMapper.getPublishInfo(id);
        return coursePublishVo;
    }

    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id 删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //2 根据课程id 删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3 根据课程id 删除描述
        courseDescriptionService.removeById(courseId);
        //4 根据课程id 删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new EchoesException(201,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//关注度
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){//关注度
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){//关注度
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(eduCoursePage,wrapper);
        List<EduCourse> items = eduCoursePage.getRecords();
        long current = eduCoursePage.getCurrent();
        long pages = eduCoursePage.getPages();
        long size = eduCoursePage.getSize();
        long total = eduCoursePage.getTotal();
        //hashNext    当前是否有下一页
        //hasPrevious 是否有上一页
        boolean hasNext = eduCoursePage.hasNext();
        boolean hasPrevious = eduCoursePage.hasPrevious();

        Map<String,Object> map =new HashMap<>();
        map.put("items",items);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    /**
     * @description: 根据课程id查询它的基本信息
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/13 8:31 AM
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
