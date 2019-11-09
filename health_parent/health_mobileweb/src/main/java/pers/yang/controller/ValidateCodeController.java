package pers.yang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.constant.RedisMessageConstant;
import pers.yang.entity.Result;
import pers.yang.util.ValidateCodeUtils;
import redis.clients.jedis.JedisPool;

/**
 * @author yf
 * @date 2019/11/3
 */
@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("sendValidateCode")
    public Result sendValidateCode(@RequestParam("telephone") String phone) {
        return handleCode(phone, RedisMessageConstant.SENDTYPE_ORDER, 600);
    }

    @RequestMapping("send2Login")
    public Result send2Login(@RequestParam("telephone") String phone) {
        return handleCode(phone, RedisMessageConstant.SENDTYPE_LOGIN, 600);
    }

    /**
     * 验证码处理
     * @param phone
     * @param key
     * @param seconds
     * @return
     */
    private Result handleCode(String phone, String key, int seconds){
        try {
            // 生成验证码
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            // 将验证码放入redis中，并设置10分钟内有效
            jedisPool.getResource().setex(phone.concat(key), seconds, code);
            // 发送短信验证码
            // SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, phone, code);
            System.out.println("------------------------------发送短信验证码---------------------------");
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
