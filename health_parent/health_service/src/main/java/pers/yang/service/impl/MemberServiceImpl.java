package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.entity.MemberReportBean;
import pers.yang.mapper.MemberMapper;
import pers.yang.pojo.Member;
import pers.yang.service.MemberService;
import pers.yang.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 * @author yf
 * @date 2019/11/4
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 通过电话号码查询用户
     * @param telephone
     * @return
     */
    @Override
    public Member findMemeberByTelephone(String telephone) {
        return memberMapper.findMemberByTelephone(telephone);
    }

    /**
     * 注册用户
     * @param member
     */
    @Override
    public void add(Member member) {
        memberMapper.registeMember(member);
    }

    /**
     * 统计会员数量
     * @return
     */
    /*@Override
    public Map getMemberReport() throws Exception {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 向前推一年
        calendar.add(Calendar.YEAR, -1);

        Map<String, Object> map = new HashMap<>(2);
        long start = System.currentTimeMillis();

        // 存储月份
        List<String> months = new ArrayList<>();
        // 存储数量
        List<Integer> monthCounts = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Date time = calendar.getTime();
            // 获取月份
            String month = DateUtils.parseDate2String(time, "yyyy-MM");
            months.add(month);
            String beginDate = month.concat("-01");
            String endDate = month.concat("-31");
            Integer count = memberMapper.findCountByDate(beginDate, endDate);
            monthCounts.add(count);
            calendar.add(Calendar.MONTH, 1);
        }

        map.put("months", months);
        map.put("monthCounts", monthCounts);
        long end = System.currentTimeMillis();
        System.out.println("共耗时 = " + (end - start));
        return map;
    }*/

    /**
     * 统计会员数量
     * @return
     */
    @Override
    public Map getMemberReport() throws Exception {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 向前推1个月
        calendar.add(Calendar.MONTH, -1);
        Date lastMonth = calendar.getTime();

        // 先前推11个月
        calendar.add(Calendar.MONTH, -11);
        Date lastYear = calendar.getTime();

        String endDate = DateUtils.parseDate2String(lastMonth, "yyyy-MM").concat("-31");
        String beginDate = DateUtils.parseDate2String(lastYear, "yyyy-MM").concat("-01");

        Map<String, Object> map = new HashMap<>();
        long start = System.currentTimeMillis();

        List<MemberReportBean> memberReportBeanList = memberMapper.findCountByDate(beginDate, endDate);

        // 存储月份
        List<String> months = new ArrayList<>();
        // 存储数量
        List<Integer> monthCounts = new ArrayList<>();
        for (MemberReportBean memberReportBean : memberReportBeanList) {
            months.add(memberReportBean.getMonth());
            monthCounts.add(memberReportBean.getMonthCount());
        }

        map.put("months", months);
        map.put("monthCounts", monthCounts);
        long end = System.currentTimeMillis();
        System.out.println("共耗时 = " + (end - start));
        return map;
    }

    public static void main(String[] args) throws Exception {



    }
}
