package com.cao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cao.POJO.Member;
import com.cao.constant.MessageConstant;
import com.cao.constant.RedisMessageConstant;
import com.cao.entity.Result;
import com.cao.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class MemberController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        try {
            //从Map当中取出telephone和验证码
            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            //从Redis当中取出验证码
            String validateCodeInRedis =
                    jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
            //判断是否登录成功（验证码是否出入正确）
            if (validateCodeInRedis!=null && validateCode!=null && validateCode.equals(validateCodeInRedis)){
                //判断当前用户是否为会员
                Member member=memberService.findByTelephone(telephone);
                //当前用户不是会员，自动完成注册
                if (member==null){
                    member=new Member();
                    member.setRegTime(new Date());
                    member.setPhoneNumber(telephone);
                    memberService.add(member);
                }
                //登录成功，写入cookie
                Cookie cookie=new Cookie("login_member_telephone",telephone);
                cookie.setPath("/");
                //设置cookie的有效期为一个月
                cookie.setMaxAge(60*60*24*30);
                response.addCookie(cookie);
                //保存会员信息到cookie当中
                String jsonOfMember = JSON.toJSON(member).toString();
                jedisPool.getResource().setex(telephone,500,jsonOfMember);
                return new Result(true,MessageConstant.LOGIN_SUCCESS);
            }else {
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
