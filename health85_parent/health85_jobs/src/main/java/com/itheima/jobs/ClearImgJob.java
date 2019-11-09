package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){

        //比较jedis中的两个set集合的差异
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String img : set) {
            //去七牛云删除图片
            QiniuUtils.deleteFileFromQiniu(img);
            //删除redis中的七牛云的集合中多余的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,img);
        }

    }

}
