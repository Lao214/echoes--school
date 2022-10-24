package com.example.eduService.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/2 6:25 PM
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo {

    private  static final long serialVersionUID=1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;


}
