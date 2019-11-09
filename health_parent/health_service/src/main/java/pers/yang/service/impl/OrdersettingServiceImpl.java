package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.mapper.OrdersettingMapper;
import pers.yang.pojo.OrderSetting;
import pers.yang.service.OrdersettingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约
 * @author yf
 * @date 2019/11/1
 */
@Service(interfaceClass = OrdersettingService.class)
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void importOrderSettings(List<OrderSetting> orderSettings) {
        if (orderSettings != null) {
            for (OrderSetting orderSetting : orderSettings) {
                // 判断日期是否已经设置过
                if (ordersettingMapper.findOrdersettingCountByDate(orderSetting.getOrderDate())>0){
                    // 执行更新操作
                    ordersettingMapper.updateOrdersettingByDate(orderSetting);
                } else {
                    // 执行添加操作
                    ordersettingMapper.addOrdersetting(orderSetting);
                }
            }
        }
    }

    /**
     * 通过年月查询预约信息
     * @param date
     * @return
     */
    @Override
    public List<Map<String, Object>> findOrdersettingByMonth(String date) {
        List<OrderSetting> orderSettingList = ordersettingMapper.findOrdersettingByMonth(date.concat("-01"), date.concat("-31"));
        List<Map<String, Object>> orderSettingMapList = new ArrayList<>();
        for (OrderSetting ordersetting : orderSettingList) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", ordersetting.getOrderDate().getDate());
            map.put("number", ordersetting.getNumber());
            map.put("reservations", ordersetting.getReservations());
            orderSettingMapList.add(map);
        }
        return orderSettingMapList;
    }

    /**
     * 设置预约天数
     * @param orderSetting
     */
    @Override
    public void updateOrdersettingByDate(OrderSetting orderSetting) {
        // 判断日期是否已经设置过
        if (ordersettingMapper.findOrdersettingCountByDate(orderSetting.getOrderDate())>0){
            // 执行更新操作
            ordersettingMapper.updateOrdersettingByDate(orderSetting);
        } else {
            // 执行添加操作
            ordersettingMapper.addOrdersetting(orderSetting);
        }
    }
}
