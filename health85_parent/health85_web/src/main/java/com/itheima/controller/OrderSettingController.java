package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("importOrderSettings")
    public Result importOrderSettings(@RequestParam("excelFile")MultipartFile file){
        try {
            //使用工具类读取模板中的数据
            List<String[]> list = POIUtils.readExcel(file);

            //定义一个集合用来存放所有的OrderSettting
            List<OrderSetting> orderSettings = new ArrayList<>();
            //判断list为空
            if (list!=null){
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting();
                    //封装日期
                    orderSetting.setOrderDate(DateUtils.parseString2Date(strings[0],"yyyy/MM/dd"));
                    //封装可预约数量
                    orderSetting.setNumber(Integer.parseInt(strings[1]));
                    orderSettings.add(orderSetting);
                }

                orderSettingService.importOrderSettings(orderSettings);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    /**
     * 显示预约数据
     */
    @RequestMapping("findOrderSettingByMonth")
    public Result findOrderSettingByMonth(@RequestParam("date")String date){

        try {
            List<Map> orderSettings = orderSettingService.findOrderSettingByMonth(date);
            return new Result(true,MessageConstant.QUERY_ORDERSETTING_SUCCESS,orderSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDERSETTING_FAIL);
        }

    }

    /**
     * 根据日期更新可预约数量
     */
    @RequestMapping("updateNumberByOrderDate")
    public Result updateNumberByOrderDate(@RequestBody OrderSetting orderSetting){

        try {
            orderSettingService.updateNumberByOrderDate(orderSetting);
            return new Result(true,MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ORDERSETTING_FAIL);
        }

    }
}
