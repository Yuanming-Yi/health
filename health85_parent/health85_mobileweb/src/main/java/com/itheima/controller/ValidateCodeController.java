package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("send2Telephone")
    public Result send2Telephone(@RequestParam("telephone")String telephone){

        try {
            //生成一个验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            //将验证码发送到对应的手机号
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);

            //设置一个存储验证码的key
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;

            //将验证码保存到redis中，并且设置一个有效时间10分钟
            jedisPool.getResource().setex(key,60*10,code);

            //响应
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    /**
     * 登陆的验证码发送
     */
    @RequestMapping("send2Login")
    public Result send2Login(@RequestParam("telephone")String telephone){

        try {
            //生成验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            //发送验证码到用户的手机
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);

            //定义保存登陆验证码的key
            String key = telephone+RedisMessageConstant.SENDTYPE_LOGIN;

            //将发送的验证码保存到redis，设置一个有效时间10分钟
            jedisPool.getResource().setex(key,60*10,code);

            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }
}
