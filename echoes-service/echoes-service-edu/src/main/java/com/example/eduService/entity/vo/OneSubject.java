package com.example.eduService.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 劳威锟
 * @version 1.0
 * @description: TODO 一级分类
 * @date 2022/9/29 4:21 PM
 */
@Data
public class OneSubject {

    private  String id;

    private  String label;

    //多个二级分类
    private List<TwoSubject> children=new ArrayList<>();

}
