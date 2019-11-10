package com.itheima.mapper;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 角色
 * @author yf
 * @date 2019/11/9
 */
public interface RoleMapper {
    /**
     * 分页查询角色列表
     * @param queryString
     * @return
     */
    List<Role> findRoleByPage(@Param("queryString") String queryString);

    /**
     * 添加角色
     * @param role
     */
    void addRole(Role role);

    /**
     * 编辑角色
     * @param role
     */
    void editRole(Role role);


    /**
     * 删除角色
     * @param id
     */
    void deleteRole(@Param("id") Integer id);

    /**
     * 添加角色和权限的关联
     * @param id
     * @param ids
     */
    void addAssociationOfRoleAndPermission(@Param("id") Integer id, @Param("ids") Integer[] ids);

    /**
     * 删除角色和权限的关联
     * @param id
     */
    void deleteAssociationOfRoleAndPermission(@Param("id") Integer id);

    /**
     * 添加角色和菜单关联
     * @param id
     * @param ids
     */
    void addAssociationOfRoleAndMenu(@Param("id") Integer id, @Param("ids") Integer[] ids);
    /**
     * 删除角色和菜单关联
     */
    void deleteAssociationOfRoleAndMenu(@Param("id") Integer id);

    /**
     * 通过用户查询菜单
     * @param id
     * @return
     */
    List<Integer> findMenuByRoleId(@Param("id") Integer id);

    /**
     * 查询角色被用户引用数量
     * @param id
     * @return
     */
    Integer findRoleCountUsedByUser(@Param("id") Integer id);

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
    List<Integer> findRoleByUserId(@Param("userId") Integer userId);
}
