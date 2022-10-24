package com.example.crmService.entity;

import lombok.Data;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2022/10/9 1:55 PM
 */
@Data
public class UcenterMemberRegisterVo {

    private String nickname;

    private String password;

    private String email;

    private String code;
}
