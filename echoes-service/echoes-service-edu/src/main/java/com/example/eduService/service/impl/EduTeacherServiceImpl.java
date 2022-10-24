package com.example.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.entity.EduTeacher;
import com.example.eduService.dao.EduTeacherMapper;
import com.example.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> eduTeacherPage) {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        //分页数据封装到pageTeacher对象
        baseMapper.selectPage(eduTeacherPage, queryWrapper);
        //取分页对象中的值
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long current = eduTeacherPage.getCurrent();
        long pages = eduTeacherPage.getPages();
        long size = eduTeacherPage.getSize();
        long total = eduTeacherPage.getTotal();
        //hashNext    当前是否有下一页
        //hasPrevious 是否有上一页
        boolean hasNext = eduTeacherPage.hasNext();
        boolean hasPrevious = eduTeacherPage.hasPrevious();

        Map<String,Object> map =new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }
}
