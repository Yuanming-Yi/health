package pers.yang.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yang.constant.RedisConstant;
import pers.yang.util.QiniuUtils;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author yf
 * @date 2019/11/1
 */
@Service
public class CleanImageJob {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 定时清理七牛云多余照片
     */
    public void cleanImg() {
        // 对两个集合中的图片进行差异比较，将多余图片清理
        Set<String> imgsToBeCleaned = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String img : imgsToBeCleaned) {
            // 调用工具类清理图片七牛云中的图片
            QiniuUtils.deleteFile(img);
            // 删除redis中的多余的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, img);
        }
    }
}
