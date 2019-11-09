package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * 预约
 * @author yf
 * @date 2019/11/1
 */
public interface OrdersettingMapper {
    /**
     * 通过日期查询预约信息是否已经设置
     * @param orderDate
     * @return
     */
    Long findOrdersettingCountByDate(@Param("orderDate") Date orderDate);

    /**
     * 更新预约信息
     * @param orderSetting
     */
    void updateOrdersettingByDate(OrderSetting orderSetting);

    /**
     * 添加预约信息
     * @param orderSetting
     */
    void addOrdersetting(OrderSetting orderSetting);

    /**
     * 通过年月查询预约信息
     * @param startDate
     * @param endDate
     * @return
     */
    List<OrderSetting> findOrdersettingByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 通过查询预约信息
     * @param orderDate
     * @return
     */
    OrderSetting findOrdersettingByDate(@Param("orderDate") String orderDate);

    /**
     * 更新日期
     * @param orderDate
     */
    void updateReservationsByOrderDate(@Param("orderDate") String orderDate);

    // /**
    //  * 查询指定日期数据数量
    //  * @param orderDate
    //  * @return
    //  */
    // long findOrderSettingCountByDate(Date orderDate);

}
