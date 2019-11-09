package pers.yang.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.yang.constant.MessageConstant;
import pers.yang.constant.RedisConstant;
import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.entity.Result;
import pers.yang.pojo.Setmeal;
import pers.yang.service.SetmealService;
import pers.yang.util.QiniuUtils;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * 套餐
 * @author yf
 * @date 2019/10/31
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        try {
            // 获得原文件名
            String originalFilename = file.getOriginalFilename();
            // 获取原文件名的后缀名
            String suffic = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 生成新的文件名
            String fileName = UUID.randomUUID().toString().concat(suffic);
            // 上传图片
            QiniuUtils.uploadFile(file.getBytes(), fileName);
            // 将上传图片名称保存到redis的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加套餐
     */
    @RequestMapping("addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] checkgroupIds) {
        try {
            setmealService.addSetmeal(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 分页查询套餐信息
     */
    @RequestMapping("findSetmealByCondition")
    public Result findSetmealByCondition(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findSetmealByCondition(queryPageBean);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


}
