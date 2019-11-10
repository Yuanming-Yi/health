package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findByUsername(String username);

    List<Map<String,Object>> findMenuByUsername(String username);

    /**
     * 分页查询用户列表
     * @param queryPageBean
     * @return
     */
    PageResult findUserByPage(QueryPageBean queryPageBean);

    /**
     * 添加用户
     * @param user
     * @param roleIds
     */
    void addUser(User user, Integer[] roleIds);

    /**
     * 编辑用户
     * @param user
     * @param roleIds
     */
    void editUser(User user, Integer[] roleIds);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Integer id);

    /**
     * 通过用户名查询用户
     * @param trim
     * @return
     */
    User findUsername(String trim);
}
