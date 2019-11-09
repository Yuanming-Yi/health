package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.mapper.MemberMapper;
import pers.yang.mapper.OrderMapper;
import pers.yang.service.ReportService;
import pers.yang.util.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yf
 * @date 2019/11/8
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    /*
         reportDate:null,//报告日期
         todayNewMember :0,//今日新增会员数
         totalMember :0,//总会员数
         thisWeekNewMember :0,//本周新增会员数
         thisMonthNewMember :0,//本月新增会员数
         todayOrderNumber :0,//今日预约数
         todayVisitsNumber :0,//今日到诊数
         thisWeekOrderNumber :0,//本周预约数
         thisWeekVisitsNumber :0,//本周到诊数
         thisMonthOrderNumber :0,//本月预约数
         thisMonthVisitsNumber :0,//本月到诊数
         hotSetmeal :[//显示4个热门套餐
         {name:"粉红珍爱(女)升级TM12项筛查体检套餐",setmeal_count:5,proportion:0.4545},
         {name:"阳光爸妈升级肿瘤12项筛查体检套餐",setmeal_count:2,proportion:0.1818},
         {name:"珍爱高端升级肿瘤12项筛查",setmeal_count:2,proportion:0.1818},
         {name:"孕前检查套餐",setmeal_count:1,proportion:0.0909}
         ]
     */
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map getBusinessReport() throws Exception {
        // 今天日期
        String reportDate = DateUtils.parseDate2String(DateUtils.getToday());
        // 本周周一
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        // 本月一号
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        // 今日新增会员数
        Long todayNewMember = memberMapper.findTodayNewMemberCount(reportDate);
        // 总会员数
        Long totalMember = memberMapper.findTotalMemberCount();
        // 本周新增会员数
        Long thisWeekNewMember = memberMapper.findNewMemberCountAfterDate(thisWeekMonday);
        // 本月新增会员数
        Long thisMonthNewMember = memberMapper.findNewMemberCountAfterDate(firstDay4ThisMonth);
        // 今日预约数
        Long todayOrderNumber = orderMapper.findTodayOrderNumber(reportDate);
        // 今日到诊数
        Long todayVisitsNumber = orderMapper.findTodayVisitsNumber(reportDate);
        // 本周预约数
        Long thisWeekOrderNumber = orderMapper.findWeekOrderNumberAfterDate(thisWeekMonday);
        // 本周到诊数
        Long thisWeekVisitsNumber = orderMapper.findiWeekVisitsNumberAfterDate(thisWeekMonday);
        // 本月预约数
        Long thisMonthOrderNumber = orderMapper.findWeekOrderNumberAfterDate(firstDay4ThisMonth);
        // 本月到诊数
        Long thisMonthVisitsNumber = orderMapper.findiWeekVisitsNumberAfterDate(firstDay4ThisMonth);
        // 四个热门套餐
        List<Map> hotSetmeal = orderMapper.findHotSetmeal(4);

        //定义map封装页面所需数据
        Map<String,Object> result = new HashMap<>();

        result.put("reportDate",reportDate);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;
    }
}
