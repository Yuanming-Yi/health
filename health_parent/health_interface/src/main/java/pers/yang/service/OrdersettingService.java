package pers.yang.service;

import pers.yang.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约
 * @author yf
 * @date 2019/11/1
 */
public interface OrdersettingService {
    /**
     * 从excel读取的数据导入数据库
     */
    void importOrderSettings(List<OrderSetting> orderSettings);

    /**
     * 通过年月查询预约信息
     * @param date
     * @return
     */
    List<Map<String,Object>> findOrdersettingByMonth(String date);

    /**
     * 设置预约天数
     * @param orderSetting
     */
    void updateOrdersettingByDate(OrderSetting orderSetting);
}
