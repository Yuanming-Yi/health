package com.itheima.mapper;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    Order findByCondition(@Param("memberId") Integer memberId, @Param("orderDate") Date orderDate, @Param("setmealId")int setmealId);

    void add(Order order);

    Map findById(@Param("id")Integer id);

    Integer findTodayOrderNumber(String today);

    Integer findOrderNumberAfterDate(String thisWeekMonday);

    Integer findTodayVisitsNumber(String today);

    Integer findVisitsNumberAfterDate(String thisWeekMonday);

    List<Map<String, Object>> findHotSetmeal();
}
