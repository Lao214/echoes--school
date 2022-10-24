package com.example.crmService.service;

import com.example.crmService.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmService.entity.UcenterMemberRegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(UcenterMemberRegisterVo umrv);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
