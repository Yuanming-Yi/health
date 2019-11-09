package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.entity.MemberReportBean;
import pers.yang.pojo.Member;

import java.util.List;

/**
 * 会员
 * @author yf
 * @date 2019/11/3
 */
public interface MemberMapper {
    /**
     * 通过手机号查询会员
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(@Param("telephone")String telephone);

    /**
     * 添加会员信息
     * @param member
     */
    void registeMember(Member member);

    /**
     * 查询指定日期会员数量
     * @param beginDate
     * @param endDate
     * @return
     */
    List<MemberReportBean> findCountByDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 查询指定日期会员数
     * @param date
     * @return
     */
    Long findTodayNewMemberCount(@Param("date") String date);

    /**
     * 查询总会员数
     * @return
     */
    Long findTotalMemberCount();

    /**
     * 指定日期之后的新增会员数
     * @param date
     * @return
     */
    Long findNewMemberCountAfterDate(@Param("date")String date);


    // Integer findCountByDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate);
}
