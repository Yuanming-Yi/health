package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.DateUtils;
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
     * @param beginMonth
     * @param endMonth
     */
    @Override
    public Map getMemberReport(String beginMonth, String endMonth) {
        //定义一个list封装每一个月的月份
        List<String> months = new ArrayList<>();

        //定义一个list封装每一个月对应的会员数量
        List<Integer> memberCounts = new ArrayList<>();

        //定义一个map,用来封装过去的12个月的月份的集合，已经每个月对应的会员数量的集合
        Map<String, List> map = new HashMap<>();

        if(beginMonth.equals("null")||endMonth.equals("null") || beginMonth==null || beginMonth.trim().equals("") || endMonth==null || endMonth.trim().equals("") ){

            //初始化日历对象
            Calendar calendar = Calendar.getInstance();
            //将日历对象往前推12个月
            calendar.add(Calendar.MONTH,-12);
            for (int i = 0; i < 12; i++) {
                //获取时间
                Date time = calendar.getTime();
                //将时间格式化为年份-月份：“2019-08”
                String date = new SimpleDateFormat("yyyy-MM").format(time);
                //将每一个月封装到集合中
                months.add(date);

                calendar.add(Calendar.MONTH,+1);
            }
        } else{


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(beginMonth);
                date2 =sdf.parse(endMonth);
            } catch (Exception e) {
                e.printStackTrace();
            }


            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(date1);
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(date2);
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);


            Calendar curr = min;
            while (curr.before(max)) {
                months.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }

        }


        for (int i = 0; i < months.size(); i++) {
            String date = months.get(i);

            //定义每一个月的查询月份的起始值
            String monthBegin = date+"-01";
            //定义每个月的结束值
            String monthEnd = date+"-31";
            //通过每个月的月份查询对应每个月的会员数量
            Integer memberCount = memberMapper.findMemberCountByMonth(monthBegin,monthEnd);

            //将每一个月对应的会员数量封装到集合中
            memberCounts.add(memberCount);
        }



        map.put("months",months);
        map.put("memberCounts",memberCounts);

        return map;
    }

    @Override
    public Map getReportMemberAge() {
        Date date = new Date();
        String dateformate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(dateformate);
        Integer firstCount=memberMapper.findMemberCountByAge(0,18,dateformate);
        Integer secondCount=memberMapper.findMemberCountByAge(18,30,dateformate);
        Integer thirdCount=memberMapper.findMemberCountByAge(30,45,dateformate);
        Integer fourthCount=memberMapper.findMemberCountByMostAge(45,dateformate);
        List<String> nameList=new ArrayList<>();
        nameList.add("0-18岁");
        nameList.add("18-30岁");
        nameList.add("30-45岁");
        nameList.add("45岁以上");
        List<Map> ageList=new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","0-18岁");
        map.put("value",firstCount);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name","18-30岁");
        map1.put("value",secondCount);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name","30-45岁");
        map2.put("value",thirdCount);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("name","45岁以上");
        map3.put("value",fourthCount);
        ageList.add(map);
        ageList.add(map1);
        ageList.add(map2);
        ageList.add(map3);
        HashMap<String, Object> allMap = new HashMap<>();
        allMap.put("memberAge",nameList);
        allMap.put("memberAllAges",ageList);
        return allMap;
    }

    @Override
    public Map getReportMemberSex() {
        Integer maleount=memberMapper.findMaleMember(1);
        Integer femaleCount=memberMapper.findMaleMember(2);
        Integer noSexCount=memberMapper.findMaleMember(3);
        Map allMap=new HashMap();
        List<String> sexnameList=new ArrayList<>();
        sexnameList.add("男性");
        sexnameList.add("女性");
        sexnameList.add("未知");
        List<Map> mapList=new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","男性");
        map.put("value",maleount);
        mapList.add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name","女性");
        map2.put("value",femaleCount);
        mapList.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name","未知");
        map3.put("value",noSexCount);
        mapList.add(map3);
        allMap.put("memberSex",sexnameList);
        allMap.put("memberAllSexs",mapList);
        return allMap;
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
