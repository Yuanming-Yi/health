package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;


import java.util.List;

/**
 * 权限
 * @author yf
 * @date 2019/11/8
 */
public interface PermissionService {
    /**
     * 查询权限列表
     * @param queryPageBean
     * @return
     */
    PageResult findPermissionByPage(QueryPageBean queryPageBean);

    /**
     * 添加权限
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 编辑权限
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 删除权限
     * @param id
     */
    void deletePermission(Integer id);

    /**
     * 获取所有权限列表
     * @return
     */
    List<Permission> findAllPermissions();

    /**
     * 通过角色id查询权限id
     * @param id
     * @return
     */
    List<Integer> findPermissionByRoleId(Integer id);
}
