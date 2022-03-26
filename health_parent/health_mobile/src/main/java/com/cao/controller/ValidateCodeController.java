package com.cao.controller;

import com.cao.constant.MessageConstant;
import com.cao.constant.RedisMessageConstant;
import com.cao.entity.Result;
import com.cao.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        //生成随机验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            //向Redis当中存储手机号和验证码对应的键值对
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,
                                    300, validateCode.toString());
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS,validateCode.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //生成随机验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            //向Redis当中存储手机号和验证码对应的键值对
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,
                    300, validateCode.toString());
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS,validateCode.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
