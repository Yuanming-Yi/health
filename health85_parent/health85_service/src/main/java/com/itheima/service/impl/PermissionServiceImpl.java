package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.PermissionMapper;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 权限管理
 * @author yf
 * @date 2019/11/8
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 查询权限列表
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPermissionByPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Permission> permissionList = permissionMapper.findPermissionByPage(queryPageBean.getQueryString());
        PageInfo pageInfo = new PageInfo(permissionList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加权限
     * @param permission
     */
    @Override
    public void addPermission(Permission permission) {
        permissionMapper.addPermission(permission);
    }

    /**
     * 编辑权限
     * @param permission
     */
    @Override
    public void editPermission(Permission permission) {
        permissionMapper.editPermission(permission);
    }

    /**
     * 删除权限
     * @param id
     */
    @Override
    public void deletePermission(Integer id) {
        // 判断当前权限是否被引用
        Integer count = permissionMapper.findPermissionCountOfUsed(id);
        if (count > 0) {
            throw new RuntimeException("当前权限被引用，无法删除！");
        }

        // 根据id删除权限
        permissionMapper.deletePermission(id);
    }

    /**
     * 获取所有的权限列表
     * @return
     */
    @Override
    public List<Permission> findAllPermissions() {

        return permissionMapper.findAllPermissions();
    }

    /**
     * 通过角色id查询权限id列表
     * @param id
     * @return
     */
    @Override
    public List<Integer> findPermissionByRoleId(Integer id) {
        return permissionMapper.findPermissionByRoleId(id);
    }
}
