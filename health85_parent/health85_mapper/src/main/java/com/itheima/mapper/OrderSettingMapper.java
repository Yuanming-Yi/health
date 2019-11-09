package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {
    //通过日期查询
    Integer findCountByOrderDate(@Param("orderDate") Date orderDate);

    //通过日期更新可预约数量
    void updateNumberByOrderDate(OrderSetting orderSetting);

    //新增可预约
    void add(OrderSetting orderSetting);

    //List<OrderSetting> findOrderSettingByMonth(@Param("dateBegin") String dateBegin,@Param("dateEnd")  String dateEnd);
    List<Map> findOrderSettingByMonth(@Param("dateBegin") String dateBegin, @Param("dateEnd")  String dateEnd);

    OrderSetting findByOrderDate(@Param("date")Date date);

    void updateReservationsByOrderDate(@Param("date")Date date);
    //定时
    void quartzClearOverdue(@Param("date")String date);
}
