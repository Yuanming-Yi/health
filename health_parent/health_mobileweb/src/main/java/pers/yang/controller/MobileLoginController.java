package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.constant.RedisMessageConstant;
import pers.yang.entity.Result;
import pers.yang.pojo.Member;
import pers.yang.service.MemberService;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Objects;

/**
 * @author yf
 * @date 2019/11/4
 */
@RestController
@RequestMapping("login")
public class MobileLoginController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("check")
    public Result check(@RequestBody ModelMap map) {
        try {
            // 获取手机号码
            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            // 获取key
            String key = telephone.concat(RedisMessageConstant.SENDTYPE_LOGIN);
            // 获取redis中的验证码
            String redisCode = jedisPool.getResource().get(key);
            // 比较验证码
            if (redisCode == null || !Objects.equals(redisCode, validateCode)) {
                return new Result(false, MessageConstant.LOGIN_ERROR);
            }
            // 判断是否是会员
            Member member = memberService.findMemeberByTelephone(telephone);
            if (member == null) {
                // 进行注册
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_ERROR);
        }
    }
}
