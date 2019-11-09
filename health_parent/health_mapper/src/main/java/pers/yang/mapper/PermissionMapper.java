package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.Permission;

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
}
