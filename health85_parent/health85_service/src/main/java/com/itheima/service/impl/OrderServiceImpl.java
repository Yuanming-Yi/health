package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private OrderMapper orderMapper;

   /*
        1、检查用户所选择的预约日期是否已经提供了预约设置，如果没有设置则无法进行预约
        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        3、通过手机号检查当前用户是否为会员
            不是会员：自动完成注册，
            是会员：检查用户是否重复预约（通过member_id、orderDate、setmeal_id，查询同一个会员在当天是不是预约了同一个套餐），如果是重复预约则无法完成再次预约
        4、添加预约
            需要手动设置预约状态、预约类型、会员编号、预约日期、套餐编号
        5、预约成功，更新当日的已预约人数
    */


    @Override
    public Order add(Map map) throws Exception {

        //获取手机号
        String telephone = (String) map.get("telephone");
        //获取预约日期
        String orderDate = (String) map.get("orderDate");
        //将日期转换为日期类型
        Date date = DateUtils.parseString2Date(orderDate);

        //通过日期查询当天有没有提供预约服务
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
        //如果不能预约，抛出异常，提示用户，当天不提供预约服务
        if (orderSetting==null){
            throw new RuntimeException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //如果可以预约，判断预约数量是否已满
        if (orderSetting.getReservations()>=orderSetting.getNumber()){
            //如果预约已满，抛出异常，提示用户预约已满
            throw new RuntimeException(MessageConstant.ORDER_FULL);
        }

        //通过手机号查询会员
        Member member = memberMapper.findByTelephone(telephone);
        //判断是否是会员
        if (member==null){
            //如果不是会员，注册为会员
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String)map.get("idCard"));
            member.setRegTime(new Date());

            //执行注册会会员
            memberMapper.add(member);
        }else{
            //获取套餐id
            String setmealId = (String) map.get("setmealId");
            //如果是会员，查询是否重复提交预约
            Order order = orderMapper.findByCondition(member.getId(),date,Integer.parseInt(setmealId));
            if (order!=null){
                throw new RuntimeException(MessageConstant.HAS_ORDERED);
            }

        }

        //添加预约订单
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType((String)map.get("orderType"));
        //执行新增定
        orderMapper.add(order);

        //更新已预约数量
        orderSettingMapper.updateReservationsByOrderDate(date);

        return order;
    }

    /**
     * 通过订单id查询订单详情
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) throws Exception {

        Map map = orderMapper.findById(id);
        Date orderDate= (Date) map.get("orderDate");
        String date = DateUtils.parseDate2String(orderDate);
        map.put("orderDate",date);
        return map;
    }
}
