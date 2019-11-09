package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Map;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    Map getMemberReport(String beginMonth, String endMonth);

    Map getReportMemberSex();

    Map getReportMemberAge();
}
