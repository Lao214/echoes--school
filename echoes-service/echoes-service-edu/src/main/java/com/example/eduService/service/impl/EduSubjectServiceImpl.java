package com.example.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduService.Listener.SubjecExcelListener;
import com.example.eduService.entity.EduSubject;
import com.example.eduService.dao.EduSubjectMapper;
import com.example.eduService.entity.vo.ExcelSubjectData;
import com.example.eduService.entity.vo.OneSubject;
import com.example.eduService.entity.vo.TwoSubject;
import com.example.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /** 
     * @description: 导入课程分类 
     * @param:  
     * @return:  
     * @author 劳威锟
     * @date: 2022/9/29 1:57 PM
     */ 
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try{
            InputStream in =file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, ExcelSubjectData.class,new SubjecExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询出所有一级分类
        QueryWrapper<EduSubject> queryWrapperOne =new QueryWrapper<>();
        queryWrapperOne.eq("parent_id","0");
        List<EduSubject> listOne=baseMapper.selectList(queryWrapperOne);

        //查询出所有二级分类
        QueryWrapper<EduSubject> queryWrapperTwo =new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id","0"); //ne 不等于
        List<EduSubject> listTwo=baseMapper.selectList(queryWrapperTwo);
        //封装一级分类
        List<OneSubject> finalSubjectList=new ArrayList<>();
        for (int i=0;i<listOne.size();i++){
            EduSubject eduSubject=listOne.get(i);
            OneSubject oneSubject=new OneSubject();
            oneSubject.setId(eduSubject.getId());
            oneSubject.setLabel(eduSubject.getTitle());
            // BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubList =new ArrayList<>();
            for (int j = 0; j <listTwo.size() ; j++) {
                EduSubject twoEduSubject= listTwo.get(j);
                //判断二级分类parentId和一级分类id是否一样
                if(twoEduSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject =new TwoSubject();
                    twoSubject.setId(twoEduSubject.getId());
                    twoSubject.setLabel(twoEduSubject.getTitle());
                    twoFinalSubList.add(twoSubject);
                }
            }
            //把一级下面的二级放到children中
            oneSubject.setChildren(twoFinalSubList);
        }

        return finalSubjectList;
    }
}
