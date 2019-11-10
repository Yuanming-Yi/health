package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.RoleMapper;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * 角色
 * @author yf
 * @date 2019/11/9
 */
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页查询角色列表
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findRoleByPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<Role> roleList = roleMapper.findRoleByPage(queryPageBean.getQueryString());

        PageInfo pageInfo = new PageInfo(roleList);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void addRole(Role role, Integer[] ids, Integer[] menuIds) {
        // 添加角色信息
        roleMapper.addRole(role);
        // 添加角色和权限关联
        roleMapper.addAssociationOfRoleAndPermission(role.getId(), ids);
        // 添加角色和菜单关联
        roleMapper.addAssociationOfRoleAndMenu(role.getId(), menuIds);
    }

    /**
     * 编辑角色
     * @param role
     */
    @Override
    public void editRole(Role role, Integer[] permissionIds, Integer[] menuIds) {
        // 编辑角色数据
        roleMapper.editRole(role);
        // 删除权限数据
        roleMapper.deleteAssociationOfRoleAndPermission(role.getId());
        // 删除菜单数据
        roleMapper.deleteAssociationOfRoleAndMenu(role.getId());
        // 重新添加权限数据
        roleMapper.addAssociationOfRoleAndPermission(role.getId(), permissionIds);
        // 重新添加菜单数据
        roleMapper.addAssociationOfRoleAndMenu(role.getId(), menuIds);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void deleteRole(Integer id) {
        // 查询角色是否被引用
        Integer countOfuser = roleMapper.findRoleCountUsedByUser(id);
        if (countOfuser > 0) {
            throw new RuntimeException("当前角色被引用，无法删除！");
        }
        // 删除和权限关联数据
        roleMapper.deleteAssociationOfRoleAndPermission(id);
        // 删除和菜单关联数据
        roleMapper.deleteAssociationOfRoleAndMenu(id);
        // 删除角色数据
        roleMapper.deleteRole(id);
    }
    /**
     * 通过用户查询菜单
     */
    @Override
    public List<Integer> findMenuByRoleId(Integer id) {

        return roleMapper.findMenuByRoleId(id);
    }

    /**
     * 查询角色列表
     * @return
     */
    @Override
    public List<Role> findAllRoles() {
        return roleMapper.findAllRoles();
    }
    /**
     * 通过用户id 查询角色
     */
    @Override
    public List<Integer> findRoleByUserId(Integer userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
