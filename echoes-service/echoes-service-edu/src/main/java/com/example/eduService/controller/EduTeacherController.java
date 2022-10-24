package com.example.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduService.entity.EduTeacher;
import com.example.eduService.entity.vo.TeacherQuery;
import com.example.eduService.service.EduTeacherService;
import com.example.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-09-27
 */
@Api(description = "讲师列表")
@RestController
@RequestMapping("/eduService/edu-teacher")
public class EduTeacherController {


    @Autowired
    private EduTeacherService eduTeacherService;

      /** 
       * @description: 查询讲师list 
       * @param:  
       * @return:  
       * @author 劳威锟
       * @date: 2022/9/27 8:52 AM
       *  rest风格
       */
      @ApiOperation(value = "查询所有讲师列表")
      @GetMapping("findAll")
      public Result findAllTeacher(){

          List<EduTeacher> list= eduTeacherService.list(null);
            return  Result.success().data("items",list);
      }

      /** 
       * @description: 逻辑删除 
       * @param:  
       * @return:  
       * @author 劳威锟
       * @date: 2022/9/27 9:55 AM
       */
      @ApiOperation(value = "根据id逻辑删除讲师")
      @DeleteMapping("{id}")
      public Result removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
          boolean flag = eduTeacherService.removeById(id);
          if(flag){
              return Result.success();
          }else {
              return Result.error();
          }
      }
      
      /** 
       * @description: 分页查询list
       * @param:  
       * @return:  
       * @author 劳威锟
       * @date: 2022/9/27 11:15 AM
       */
      @ApiOperation(value = "分页查询讲师list")
      @GetMapping("getTeacherListPage/{current}/{limit}")
      public  Result getTeacherListPage(@PathVariable long current, @PathVariable long limit){
          //current 当前页  size 每页查询记录数
          Page<EduTeacher> eduTeacherPage =new Page<>(current,limit);
          /**调用分页方法**/
          /**调用方法的时候，底层封装，把分页的所有数据封装到eduTeacherPage对象里面**/
          eduTeacherService.page(eduTeacherPage,null);
          /**total 为所有记录**/
          long total =eduTeacherPage.getTotal();
          List<EduTeacher> list=eduTeacherPage.getRecords();
          return  Result.success().data("rows",list).data("total",total);
      }


    /**
     * @description: 根据条件分页查询list
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/9/27 11:15 AM
     * requestBody  使用json传递数据，把json数据封装到对应的对象里面 需要用post提交方式
     * responseBody 返回数据，返回json数据
     */
    @ApiOperation(value = "根据条件分页查询list")
    @PostMapping("queryTeacherListPage/{current}/{limit}")
    public  Result queryTeacherListPage(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) TeacherQuery teacherQuery){
        /**创建page对象**/
        Page<EduTeacher> eduTeacherPage =new Page<>(current,limit);
        /**构建条件**/
        QueryWrapper<EduTeacher> queryWrapper =new QueryWrapper<>();
        String  name = teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String  begin=teacherQuery.getBegin();
        String  end  =teacherQuery.getEnd();
        /**判断是否为空，如果为空，不拼接条件**/
        if(!StringUtils.isEmpty(name)){
            //构建条件
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            //构建条件
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            //构建条件
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            //构建条件
            queryWrapper.le("gmt_create",end);
        }
        //排序 根据创建时间降序
        queryWrapper.orderByDesc("gmt_create");
        /**调用分页方法**/
        eduTeacherService.page(eduTeacherPage,queryWrapper);
        /**total 为所有记录**/
        long total =eduTeacherPage.getTotal();
        List<EduTeacher> list=eduTeacherPage.getRecords();
        return  Result.success().data("rows",list).data("total",total);
    }

    /**
     * @description: 添加讲师
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/9/27 1:31 PM
     */
    @PostMapping("addTeacher")
    @ApiOperation(value = "添加讲师")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
       boolean save=  eduTeacherService.save(eduTeacher);
       if(save){
           return Result.success();
       }else {
           return Result.error();
       }
    }

    /**
     * @description: 根据id查询
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/9/27 1:46 PM
     */
    @GetMapping("getTeacherById/{id}")
    @ApiOperation(value = "根据id查询讲师")
    public Result getTeacher(@PathVariable String id){
       EduTeacher eduTeacher=  eduTeacherService.getById(id);
       return Result.success().data("teacher",eduTeacher);
    }

    /**
     * @description: 修改讲师信息
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/9/27 1:46 PM
     */
    @PostMapping("updateTeacher")
    @ApiOperation(value = "修改讲师信息")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag=  eduTeacherService.updateById(eduTeacher);
        if(flag){
            return Result.success();
        }else {
            return Result.error();
        }

    }

}

