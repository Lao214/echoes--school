package com.example.crmService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.crmService.entity.CrmBanner;
import com.example.crmService.entity.vo.BannerQuery;
import com.example.crmService.service.CrmBannerService;
import com.example.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-07
 */
@RestController
@RequestMapping("/crmService/crm-banner")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;


    @GetMapping("pageBanner/{current}/{limit}")
    public Result pageBanner(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) BannerQuery bannerQuery){
        /**创建page对象**/
        Page<CrmBanner> eduCoursePage =new Page<>(current,limit);
        /**构建条件**/
        QueryWrapper<CrmBanner> queryWrapper =new QueryWrapper<>();
        String  title    = bannerQuery.getTitle();
        Integer deleted  = bannerQuery.getIsDeleted();
        String  begin    = bannerQuery.getBegin();
        String  end      = bannerQuery.getEnd();
        /**判断是否为空，如果为空，不拼接条件**/
        if(!StringUtils.isEmpty(title)){
            //构建条件
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(deleted)){
            //构建条件
            queryWrapper.eq("deleted",deleted);
        }
        if(!StringUtils.isEmpty(begin)){
            //构建条件
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            //构建条件
            queryWrapper.le("gmt_create",end);
        }
        /**排序 根据创建时间降序**/
        queryWrapper.orderByDesc("gmt_create");
        /**调用分页方法**/
        crmBannerService.page(eduCoursePage,queryWrapper);
        /**total 为所有记录**/
        long total =eduCoursePage.getTotal();
        List<CrmBanner> list=eduCoursePage.getRecords();
        return  Result.success().data("rows",list).data("total",total);
    }


    // 添加banner
    @PostMapping("addBanner")
    public  Result addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return Result.success();
    }

    @PutMapping("update")
    public Result updateById(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return Result.success();
    }


    @DeleteMapping("remove/{id}")
    public Result remove(@RequestBody CrmBanner crmBanner){
        crmBannerService.removeById(crmBanner);
        return Result.success();
    }

}

