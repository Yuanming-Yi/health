package com.itheima.mapper;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    Member findByTelephone(@Param("telephone") String telephone);

    void add(Member member);

    Integer findMemberCountByMonth(@Param("monthBegin")String monthBegin,@Param("monthEnd") String monthEnd);

    Integer findTodayNewMember(@Param("today")String today);

    Integer findTotalMember();

    Integer findNewMemberCountAfterDate(@Param("date")String date);

    Integer findMaleMember(@Param("sex")int i);

    Integer findMemberCountByAge(@Param("startAge")int i,@Param("endAge") int i1, @Param("date")String dateformate);

    Integer findMemberCountByMostAge(@Param("startAge")int i, @Param("date")String dateformate);
}
