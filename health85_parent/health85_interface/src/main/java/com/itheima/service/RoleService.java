package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;


import java.util.List;

/**
 * 角色
 * @author yf
 * @date 2019/11/9
 */
public interface RoleService {
    /**
     * 分页查询角色列表
     * @param queryPageBean
     * @return
     */
    PageResult findRoleByPage(QueryPageBean queryPageBean);

    /**
     * 添加角色
     * @param role
     */
    void addRole(Role role, Integer[] permissionIds, Integer[] menuIds);

    /**
     * 编辑角色
     * @param role
     */
    void editRole(Role role, Integer[] permissionIds, Integer[] menuIds);

    /**
     * 删除角色
     * @param id
     */
    void deleteRole(Integer id);

    /**
     * 通过用户查询菜单
     * @param id
     * @return
     */
    List<Integer> findMenuByRoleId(Integer id);

    /**
     * 查询角色列表
     * @return
     */
    List<Role> findAllRoles();

    /**
     * 通过用户id查询角色
     * @param userId
     * @return
     */
    List<Integer> findRoleByUserId(Integer userId);
}
