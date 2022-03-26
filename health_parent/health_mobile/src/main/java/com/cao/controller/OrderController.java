package com.cao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cao.POJO.Order;
import com.cao.constant.MessageConstant;
import com.cao.constant.RedisMessageConstant;
import com.cao.entity.Result;
import com.cao.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //校验手机验证码
        String validateCode = (String) map.get("validateCode");
        if (validateCode != null && validateCodeInRedis != null && validateCode.equals(validateCodeInRedis)) {
            //调用体检预约服务
            try {
                map.put("orderType", Order.ORDERTYPE_WEIXIN);
                Result result = orderService.order(map);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                //预约失败
                return new Result(false, MessageConstant.ORDER_FAIL);
            }
        } else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map=orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
