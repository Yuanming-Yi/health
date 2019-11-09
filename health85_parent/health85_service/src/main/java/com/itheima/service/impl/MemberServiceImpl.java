package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 通过手机号查询会员
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }

    /**
     * 新增
     * @param member
     */
    @Override
    public void add(Member member) {

        memberMapper.add(member);

    }

    /**
     * 获取过去12个月每月的会员数量
     * @return
     */
    @Override
    public Map getMemberReport() {
        //初始化日历对象
        Calendar calendar = Calendar.getInstance();
        //将日历对象往前推12个月
        calendar.add(Calendar.MONTH,-12);


        //定义一个list封装每一个月的月份
        List<String> months = new ArrayList<>();

        //定义一个list封装每一个月对应的会员数量
        List<Integer> memberCounts = new ArrayList<>();

        //定义一个map,用来封装过去的12个月的月份的集合，已经每个月对应的会员数量的集合
        Map<String, List> map = new HashMap<>();

        for (int i = 0; i < 12; i++) {
            //获取时间
            Date time = calendar.getTime();
            //将时间格式化为年份-月份：“2019-08”
            String date = new SimpleDateFormat("yyyy-MM").format(time);
            //将每一个月封装到集合中
            months.add(date);

            //定义每一个月的查询月份的起始值
            String monthBegin = date+"-01";
            //定义每个月的结束值
            String monthEnd = date+"-31";
            //通过每个月的月份查询对应每个月的会员数量
            Integer memberCount = memberMapper.findMemberCountByMonth(monthBegin,monthEnd);

            //将每一个月对应的会员数量封装到集合中
            memberCounts.add(memberCount);

            calendar.add(Calendar.MONTH,+1);

        }

        map.put("months",months);
        map.put("memberCounts",memberCounts);

        return map;
    }

    public static void main(String[] args) {
        //初始化日历对象
        Calendar calendar = Calendar.getInstance();
        //将日历对象往前推12个月
        calendar.add(Calendar.MONTH,-12);

        for (int i = 0; i < 12; i++) {
            //获取时间
            Date time = calendar.getTime();
            //将时间格式化为年份-月份：“2019-08”
            String date = new SimpleDateFormat("yyyy-MM").format(time);

            calendar.add(Calendar.MONTH,+1);

            System.out.println(date);
        }

    }
}
