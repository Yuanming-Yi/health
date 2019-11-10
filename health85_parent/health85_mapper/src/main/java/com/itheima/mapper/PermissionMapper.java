package com.itheima.mapper;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 权限管理
 * @author yf
 * @date 2019/11/8
 */
public interface PermissionMapper {
    /**
     * 分页查询权限列表
     * @param queryString
     * @return
     */
    List<Permission> findPermissionByPage(@Param("queryString") String queryString);

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
     * 根据id删除权限
     * @param id
     */
    void deletePermission(@Param("id") Integer id);

    /**
     * 查询id被引用的数量
     * @param id
     * @return
     */
    Integer findPermissionCountOfUsed(@Param("id") Integer id);

    /**
     * 获取所有的权限列表
     * @return
     */
    List<Permission> findAllPermissions();

    /**
     * 通过角色id查询权限id列表
     * @param id
     * @return
     */
    List<Integer> findPermissionByRoleId(@Param("id") Integer id);
}
