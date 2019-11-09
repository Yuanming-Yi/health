package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.yang.constant.MessageConstant;
import pers.yang.entity.Result;
import pers.yang.pojo.OrderSetting;
import pers.yang.service.OrdersettingService;
import pers.yang.util.DateUtils;
import pers.yang.util.POIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yf
 * @date 2019/11/1
 */
@RestController
@RequestMapping("ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("importOrderSettings")
    public Result importOrderSettings(@RequestParam("excelFile") MultipartFile file) {
        try {
            // 读取excel文件
            List<String[]> orderSettingList = POIUtils.readExcel(file);
            if (orderSettingList != null) {
                List<OrderSetting> orderSettings = toOrdersettings(orderSettingList);
                // 将文件数据保存到数据库
                ordersettingService.importOrderSettings(orderSettings);
            }
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    @RequestMapping("findOrdersettingByMonth")
    public Result findOrdersettingByMonth(@RequestParam("date") String date) {
        try {
            List<Map<String, Object>> ordersettingList = ordersettingService.findOrdersettingByMonth(date);
            return new Result(true, MessageConstant.QUERY_ORDERSETTING_SUCCESS, ordersettingList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDERSETTING_SUCCESS);
        }
    }

    /**
     * 将从excel之中读取的数据封装成list集合
     * @param orderSettingList
     * @return
     */
    private List<OrderSetting> toOrdersettings(List<String[]> orderSettingList) throws Exception {
        List<OrderSetting> orderSettings = new ArrayList<>();
        for (String[] rows : orderSettingList) {
            OrderSetting orderSetting = new OrderSetting();
            // 设置日期
            orderSetting.setOrderDate(DateUtils.parseString2Date(rows[0], "yyyy/MM/dd"));
            orderSetting.setNumber(Integer.parseInt(rows[1]));
            orderSettings.add(orderSetting);
        }
        return orderSettings;
    }

    /**
     * 通过日期修改预约天数
     */
    @RequestMapping("updateOrdersettingByDate")
    public Result updateOrdersettingByDate(@RequestBody OrderSetting orderSetting) {
        try {
            ordersettingService.updateOrdersettingByDate(orderSetting);
            return new Result(true, MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_ORDERSETTING_FAIL);
        }
    }

}
