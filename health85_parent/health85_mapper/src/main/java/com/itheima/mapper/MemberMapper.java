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
}
