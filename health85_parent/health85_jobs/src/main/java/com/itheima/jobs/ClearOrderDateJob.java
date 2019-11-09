package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
aa
@Component
public class ClearOrderDateJob {
    @Reference
    private OrderSettingService orderSettingService;
    public void clearOrderDate() {
        Date d = new Date();
        String data = new SimpleDateFormat("yyyy-MM-dd").format(d);
        orderSettingService.quartzClearOverdue(data);
    }

}
