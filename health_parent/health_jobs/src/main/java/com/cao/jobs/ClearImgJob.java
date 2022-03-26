package com.cao.jobs;

import com.cao.constant.RedisConstant;
import com.cao.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        Set<String> diff =
                jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                        RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (diff != null && !diff.isEmpty()) {
            for (String picName : diff) {
                QiniuUtils.deleteFileFromQiniu(picName);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, picName);
                System.out.println("冗余资源" + picName + "已被清除...");
            }
        }
    }
}
