package pers.yang.service;

import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.pojo.Permission;

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
}
