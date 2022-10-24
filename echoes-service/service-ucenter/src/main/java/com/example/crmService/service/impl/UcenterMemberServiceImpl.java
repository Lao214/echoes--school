package com.example.crmService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.base.exceptionhandler.EchoesException;
import com.example.crmService.entity.UcenterMember;
import com.example.crmService.dao.UcenterMemberMapper;
import com.example.crmService.entity.UcenterMemberRegisterVo;
import com.example.crmService.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.JwtUtils;
import com.example.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2022-10-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public String login(UcenterMember ucenterMember) {
        String email =ucenterMember.getEmail();
        String password=ucenterMember.getPassword();
        //邮件和密码的非空判断
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            throw  new EchoesException(201,"登录失败,密码或者账号为空");
        }
        //判断邮箱是否正确
        QueryWrapper<UcenterMember> wrapper= new QueryWrapper<>();
        wrapper.eq("email",email);
        UcenterMember um = baseMapper.selectOne(wrapper);
        if(um==null){
            throw  new EchoesException(201,"没有这个账号");
        }
        //把输入的密码进行加密 密码加密一般用MD5  特点：只能加密不能解密
        // 判断密码
        if(!MD5.encrypt(password).equals(um.getPassword())){
            throw new EchoesException(201,"密码错误");
        }
        //判断是否禁用
        if(um.getIsDeleted()){
            throw new EchoesException(201,"用户已被禁用");
        }
        //登录成功  生成token
        String token = JwtUtils.getJwtToken(um.getId(), um.getNickname());
        return token;
    }

    @Override
    public void register(UcenterMemberRegisterVo umrv) {
        //获取注册的数据
        String code     = umrv.getCode();
        String email    = umrv.getEmail();
        String password = umrv.getPassword();
        String nickname = umrv.getNickname();
        //非空判断
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw  new EchoesException(201,"注册失败,有参数为空");
        }
        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(redisCode)){
            throw new EchoesException(201,"验证码错误");
        }
        //判断邮箱是否重复，表里面存在相同邮箱就不行
        QueryWrapper<UcenterMember> wrapper =new QueryWrapper<>();
        wrapper.eq("email",email);
        Integer count = baseMapper.selectCount(wrapper);
        if(count!=0){
            throw new EchoesException(201,"邮箱已经被占用");
        }
        //插入数据库
        UcenterMember ucenterMember =new UcenterMember();
        ucenterMember.setEmail(email);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDeleted(false);
        ucenterMember.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper =new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember  ;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
