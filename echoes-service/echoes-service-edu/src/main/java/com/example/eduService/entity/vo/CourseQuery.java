package com.example.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 劳威锟
 * @version 1.0
 * @description: TODO
 * @date 2022/9/27 11:38 AM
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称，模糊查询")
    private String title;

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String teacher;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "查询开始时间",example = "2022-02-27 10:22:12")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2022-02-28 10:22:12")
    private String end;
}
