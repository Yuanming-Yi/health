package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void importOrderSettings(List<OrderSetting> orderSettings);

    List<Map> findOrderSettingByMonth(String date);

    void updateNumberByOrderDate(OrderSetting orderSetting);
    //定时删除之前的预约信息
    void quartzClearOverdue(String date);


}
