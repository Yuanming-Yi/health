package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.User;

/**
 * 用户
 * @author yf
 * @date 2019/11/7
 */
public interface UserMapper {
    /**
     * 通过用户名用户信息
     * @param username
     * @return
     */
    User findUserByUsername(@Param("username") String username);
}
