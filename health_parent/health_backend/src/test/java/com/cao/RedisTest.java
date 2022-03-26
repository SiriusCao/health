package com.cao;

import com.cao.constant.RedisConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring-mvc.xml")
public class RedisTest {
    @Autowired
    JedisPool jedisPool;

    @Test
    public void test1(){
//        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,"1234546");
    }
}
