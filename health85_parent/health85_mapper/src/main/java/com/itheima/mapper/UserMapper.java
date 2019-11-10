package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User findByUsername(@Param("username") String username);

    List<Map<String,Object>> findFirstMenuByUsername(@Param("username") String username);

    List<Map<String,Object>> findSecondMenuByUsernameAndPath(@Param("username") String username, @Param("path") String path);

    /**
     * 分页查询用户列表
     * @param queryString
     * @return
     */
    List<User> findUserByPage(@Param("queryString") String queryString);

    /**
     * 添加用户信息
     * @param user
     */
    void addUser(User user);

    /**
     * 添加用户和角色关联
     * @param id
     * @param roleIds
     */
    void addAssociationOfUserAndRole(@Param("id") Integer id, @Param("roleIds") Integer[] roleIds);

    /**
     * 编辑用户信息
     * @param user
     */
    void editUser(User user);

    /**
     * 删除用户和角色关联
     * @param id
     * @param id
     */
    void deleteAssociationOfUserAndRole(@Param("id") Integer id);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(@Param("id") Integer id);

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUsername(@Param("username") String username);
}
