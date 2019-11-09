package pers.yang.service;

import pers.yang.pojo.User;

/**
 * 用户
 * @author yf
 * @date 2019/11/7
 */
public interface UserService {
    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
