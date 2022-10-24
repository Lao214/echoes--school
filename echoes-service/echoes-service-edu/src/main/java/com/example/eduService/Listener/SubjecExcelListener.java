package com.example.eduService.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.base.exceptionhandler.EchoesException;
import com.example.eduService.entity.EduSubject;
import com.example.eduService.entity.vo.ExcelSubjectData;
import com.example.eduService.service.EduSubjectService;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/29 2:02 PM
 */
public class SubjecExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    //因为SubjecExcelListener不能交给spring管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public EduSubjectService subjectService;

    public SubjecExcelListener(EduSubjectService subjectService){
        this.subjectService=subjectService;
    }

    public SubjecExcelListener(){

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject oneEduSubject= subjectService.getOne(queryWrapper);
        return oneEduSubject;
    }


    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        EduSubject twoEduSubject= subjectService.getOne(queryWrapper);
        return twoEduSubject;
    }


    //读取excel内容，一行一行地来
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData==null){
            throw  new EchoesException(201,"文件数据为空");
        }
        //先判断一级分类是否重复
        EduSubject eduSubjectOne= this.existOneSubject(subjectService,excelSubjectData.getOneSubjectName());
        if(eduSubjectOne==null){//没有相同一级分类，进行添加
            eduSubjectOne =new EduSubject();
            eduSubjectOne.setParentId("0");
            eduSubjectOne.setTitle(excelSubjectData.getOneSubjectName());
            subjectService.save(eduSubjectOne);
        }
        //获取一级分类的id值
        String pid=eduSubjectOne.getId();
        EduSubject eduSubjectTwo= this.existTwoSubject(subjectService,excelSubjectData.getTwoSubjectName(),pid);
        if(eduSubjectTwo==null){//没有相同二级分类，进行添加
            eduSubjectTwo =new EduSubject();
            eduSubjectTwo.setParentId(pid);
            eduSubjectTwo.setTitle(excelSubjectData.getTwoSubjectName());
            subjectService.save(eduSubjectTwo);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
