package pers.yang.service;

import pers.yang.pojo.Member;

import java.util.Map;

/**
 * 用户
 * @author yf
 * @date 2019/11/4
 */
public interface MemberService {
    /**
     * 通过电话号码查询用户
     * @param telephone
     * @return
     */
    Member findMemeberByTelephone(String telephone);
    /**
     * 注册用户
     */
    void add(Member member);

    /**
     * 统计会员数量
     * @return
     */
    Map getMemberReport() throws Exception;
}
