package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import pers.yang.constant.MessageConstant;
import pers.yang.mapper.MemberMapper;
import pers.yang.mapper.OrderMapper;
import pers.yang.mapper.OrdersettingMapper;
import pers.yang.pojo.Member;
import pers.yang.pojo.Order;
import pers.yang.pojo.OrderSetting;
import pers.yang.service.OrderService;
import pers.yang.util.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * 预约套餐
 * @author yf
 * @date 2019/11/3
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    /**
     * 预约套餐
     * @param map
     * @return
     */
    @Override
    public Order add(ModelMap map) {
        // 检查预约当天是否提供预约位置
        // 获取日期
        String orderDate = (String) map.get("orderDate");
        // 通过日期查询
        OrderSetting orderSetting = ordersettingMapper.findOrdersettingByDate(orderDate);
        if (orderSetting == null) {
            // 该日期没有提供预约
            throw new RuntimeException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            // 改日期预约位置已满
            throw new RuntimeException(MessageConstant.ORDER_FULL);
        }
        // 通过手机号码查询会员
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findMemberByTelephone(telephone);
        if (null == member) {
            // 该用户未注册，直接为该用户创建账户
            member = new Member();
            member.setName((String) map.get("name"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberMapper.registeMember(member);
        }

        // 判断用户是否重复预约
        Order order = new Order();
        order.setMemberId(member.getId());
        try {
            order.setOrderDate(DateUtils.parseString2Date((String) map.get("orderDate")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        Order orderResult = orderMapper.findOrderByCondition(order);
        if (orderResult != null) {
            throw new RuntimeException(MessageConstant.HAS_ORDERED);
        }

        // 添加预约
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderMapper.add(order);

        // 更新数据
        ordersettingMapper.updateReservationsByOrderDate(orderDate);
        return order;
    }
    /**
     * 通过id查询订单信息
     */
    @Override
    public Map<String, Object> findOrderById(Integer id) {
        return orderMapper.findOrderById(id);
    }
}
