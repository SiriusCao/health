package com.cao.jobs;

import com.cao.constant.RedisConstant;
import com.cao.utils.QiniuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext-redis.xml")
public class JobTest {
    @Autowired
    JedisPool jedisPool;
    @Test
    public void test1(){
//        Set<String> diff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
//        System.out.println(diff);
//        jedisPool.getResource().sadd("+++","---");
    }
}
