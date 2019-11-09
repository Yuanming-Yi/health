package pers.yang.service;

import org.springframework.ui.ModelMap;
import pers.yang.pojo.Order;

import java.util.Map;

/**
 * 预约套餐
 * @author yf
 * @date 2019/11/3
 */
public interface OrderService {
    /**
     * 预约套餐
     * @param map
     * @return
     */
    Order add(ModelMap map);

    /**
     * 通过id查询预约订单信息
     * @param id
     * @return
     */
    Map<String,Object> findOrderById(Integer id);
}
