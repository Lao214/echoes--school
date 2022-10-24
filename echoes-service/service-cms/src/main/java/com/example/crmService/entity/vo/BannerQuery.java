package com.example.crmService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/8 8:12 AM
 */
@Data
public class BannerQuery {

    @ApiModelProperty(value = "图片名称")
    private String title;

    @ApiModelProperty(value = "状态")
    private Integer isDeleted;

    @ApiModelProperty(value = "查询开始时间",example = "2022-02-27 10:22:12")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2022-02-28 10:22:12")
    private String end;
}
