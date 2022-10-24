package com.example.eduService.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/29 1:57 PM
 */
@Data
public class ExcelSubjectData {

    @ExcelProperty(index=0)
    private String oneSubjectName;

    @ExcelProperty(index=1)
    private String twoSubjectName;
}
