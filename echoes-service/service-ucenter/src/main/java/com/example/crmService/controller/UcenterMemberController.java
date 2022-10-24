package com.example.crmService.controller;


import com.example.crmService.entity.UcenterMember;
import com.example.crmService.entity.UcenterMemberRegisterVo;
import com.example.crmService.service.UcenterMemberService;
import com.example.utils.JwtUtils;
import com.example.utils.Result;
import com.example.vo.UcenterMemberOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-09
 */
@RestController
@RequestMapping("/eduUCenter/uCenter-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;


    /**
     * @description: 登录方法
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/9 10:45 AM
     */

    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.login(ucenterMember);
        return Result.success().data("token",token);
    }


    /**
     * @description: 注册方法
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/9 10:45 AM
     */
    @PostMapping("register")
    public Result registerUser(@RequestBody UcenterMemberRegisterVo umrv){
        ucenterMemberService.register(umrv);
        return Result.success();
    }

    /**
     * @description: 根据token获取用户信息
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2022/10/9 2:38 PM
     */
    @GetMapping("getMemberInfo")
    public Result  getMemberIdByToken(HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return Result.success().data("userInfo",ucenterMember);
    }

    //根据用户id获取到用户信息
    @GetMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember um = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(um,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day){
       Integer dayCount = ucenterMemberService.countRegisterDay(day);
       return Result.success().data("countRegister",dayCount);
    }


}

