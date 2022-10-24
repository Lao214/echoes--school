package com.example.smsService.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/9/26 9:33 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "validation")
public class Validation {
    private static final long serialVersionUID = -40356785423868312L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String email;

    private String code;

    private Integer type;

    private Date   time;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
