package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.entity.Result;
import pers.yang.pojo.Permission;
import pers.yang.service.PermissionService;

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
            return new Result(true, MessageConstant.QUERY_PERMISSION_LIST_FAIL);

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
            return new Result(true, MessageConstant.ADD_PERMISSION_FAIL);
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
            return new Result(true, MessageConstant.EDIT_PERMISSION_FAIL);
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
            return new Result(true, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }
}
