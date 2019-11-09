package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void importOrderSettings(List<OrderSetting> orderSettings) {

        //通过日期查询，之前是否保存过
        for (OrderSetting orderSetting : orderSettings) {
           Integer count =  orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
           if (count>0){
               //如果之前已经保存过，那么就执行更新数量
               orderSettingMapper.updateNumberByOrderDate(orderSetting);
           }else{
               //如果没有保存过，就执行新增
               orderSettingMapper.add(orderSetting);
           }
        }

    }

    /**
     * 显示预约设置数据
     * @param date
     * @return
     */
    /*@Override
    public List<Map> findOrderSettingByMonth(String date) {

        String dateBegin = date+"-01";
        String dateEnd = date+"-31";

        List<OrderSetting> orderSettings = orderSettingMapper.findOrderSettingByMonth(dateBegin,dateEnd);


        List<Map> maps = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map<String, Object> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            maps.add(map);
        }

        return maps;
    }*/

    @Override
    public List<Map> findOrderSettingByMonth(String date) {

        String dateBegin = date+"-01";
        String dateEnd = date+"-31";

        List<Map> orderSettings = orderSettingMapper.findOrderSettingByMonth(dateBegin,dateEnd);

        return orderSettings;
    }

    @Override
    public void quartzClearOverdue(String date) {
        orderSettingMapper.quartzClearOverdue(date);
    }

    /**
     * 根据日期更新可预约数量
     * @param orderSetting
     */
    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {

        Integer count =  orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count>0){
            //如果之前已经保存过，那么就执行更新数量
            orderSettingMapper.updateNumberByOrderDate(orderSetting);
        }else{
            //如果没有保存过，就执行新增
            orderSettingMapper.add(orderSetting);
        }

    }
}
