package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * 预约
 * @author yf
 * @date 2019/11/3
 */
public interface OrderMapper {
    /**
     * 通过条件查询订单
     * @param order
     * @return
     */
    Order findOrderByCondition(Order order);

    /**
     * 添加预约订单
     * @param order
     */
    void add(Order order);

    /**
     * 通过 id 查询订单信息
     * @param id
     * @return
     */
    Map<String,Object> findOrderById(@Param("id") Integer id);

    /**
     * 查询指定日期预约数
     * @param date
     * @return
     */
    Long findTodayOrderNumber(@Param("date")String date);

    /**
     * 指定日期到诊数
     * @param date
     * @return
     */
    Long findTodayVisitsNumber(@Param("date")String date);

    /**
     * 查询指定日期之后的预约数
     * @param date
     * @return
     */
    Long findWeekOrderNumberAfterDate(@Param("date")String date);

    /**
     * 查询指定日期之后的到诊数
     * @param date
     * @return
     */
    Long findiWeekVisitsNumberAfterDate(@Param("date")String date);

    /**
     * 查询指定数量的热门套餐
     * @return
     */
    List<Map> findHotSetmeal(@Param("count") int count);
}
