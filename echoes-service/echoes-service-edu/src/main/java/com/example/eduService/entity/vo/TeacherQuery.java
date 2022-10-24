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
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2022-02-27 10:22:12")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2022-02-28 10:22:12")
    private String end;
}
