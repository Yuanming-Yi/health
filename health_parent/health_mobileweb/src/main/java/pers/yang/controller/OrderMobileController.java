package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.constant.RedisMessageConstant;
import pers.yang.entity.Result;
import pers.yang.pojo.Order;
import pers.yang.service.OrderService;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Objects;

/**
 * 预约套餐
 * @author yf
 * @date 2019/11/3
 */
@RestController
@RequestMapping("order")
public class OrderMobileController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("add")
    public Result add(@RequestBody ModelMap map) {
        try {
            // 获取手机号
            String telephone = (String) map.get("telephone");
            // 获取redis验证码
            String redisCode = jedisPool.getResource().get(telephone.concat(RedisMessageConstant.SENDTYPE_ORDER));
            // 获取用户输入的验证码
            String code = (String) map.get("validateCode");
            if (!Objects.equals(code, redisCode)) {
                // 验证码不正确
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            // 预约
            Order order = orderService.add(map);
            System.out.println("----------------"+map.get("orderDate"));
            // 发送短信告知用户预约成功
            // SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, redisCode);
            System.out.println("-------------------------------发送预约成功短信--------------------------------------------");
            return new Result(true, MessageConstant.ADD_ORDER_SUCCESS, order);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ORDER_FAIL);
        }
    }

    /**
     * 通过id查询订单信息
     */
    @RequestMapping("findOrderById")
    public Result findOrderById(@RequestParam("id") Integer id) {
        try {
            Map<String, Object> map = orderService.findOrderById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
