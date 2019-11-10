package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * 权限管理
 * @author yf
 * @date 2019/11/8
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    /**
     * 分页查询权限列表
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPermissionByPage")
    public Result findPermissionByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = permissionService.findPermissionByPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_PERMISSION_LIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_LIST_FAIL);

        }
    }

    /**
     * 添加权限
     */
    @RequestMapping("addPermission")
    public Result addPermission(@RequestBody Permission permission) {
        try {
            permissionService.addPermission(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
    }

    /**
     * 编辑权限
     */
    @RequestMapping("editPermission")
    public Result editPermission(@RequestBody Permission permission) {
        try {
            permissionService.editPermission(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }

    /**
     * 删除权限
     */
    @RequestMapping("deletePermission")
    public Result deletePermission(@RequestParam("id") Integer id) {
        try {
            permissionService.deletePermission(id);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    /**
     * 查询所有的权限列表
     */
    @RequestMapping("findAllPermissions")
    public Result findAllPermissions() {
        try {
            List<Permission> permissionList = permissionService.findAllPermissions();
            return new Result(true, MessageConstant.QUERY_PERMISSION_LIST_SUCCESS, permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_LIST_FAIL);
        }
    }

    /**
     * 通过roleid查询权限列表
     */
    @RequestMapping("findPermissionByRoleId")
    public Result findPermissionByRoleId(@RequestParam("id") Integer id) {
        try {
            List<Integer> permissionIds = permissionService.findPermissionByRoleId(id);
            return new Result(true, MessageConstant.QUERY_PERMISSION_LIST_SUCCESS, permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_LIST_FAIL);
        }
    }
}
