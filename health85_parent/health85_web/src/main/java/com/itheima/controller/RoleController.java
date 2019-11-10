package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @author yf
 * @date 2019/11/9
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     * 查询角色列表
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findRoleByPage")
    public Result findRoleByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = roleService.findRoleByPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_ROLE_LIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLE_LIST_FAIL);
        }
    }

    /**
     * 添加角色
     */
    @RequestMapping("addRole")
    public Result addRole(@RequestBody ModelMap map) {
        try {
            String roleJson = JSON.toJSONString(map.get("role"));
            String permissionIdsJson = JSON.toJSONString(map.get("permissionIds"));
            String menuIdsJson = JSON.toJSONString(map.get("menuIds"));
            Role role = JSON.parseObject(roleJson, Role.class);
            Integer[] permissionIds = JSON.parseObject(permissionIdsJson, Integer[].class);
            Integer[] menuIds = JSON.parseObject(menuIdsJson, Integer[].class);
            roleService.addRole(role, permissionIds, menuIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    /**
     * 编辑角色
     */
    @RequestMapping("editRole")
    public Result editRole(@RequestBody ModelMap map) {
        try {
            String roleJson = JSON.toJSONString(map.get("role"));
            String permissionIdsJson = JSON.toJSONString(map.get("permissionIds"));
            String menuIdsJson = JSON.toJSONString(map.get("menuIds"));
            Role role = JSON.parseObject(roleJson, Role.class);
            Integer[] permissionIds = JSON.parseObject(permissionIdsJson, Integer[].class);
            Integer[] menuIds = JSON.parseObject(menuIdsJson, Integer[].class);
            roleService.editRole(role, permissionIds, menuIds);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    /**
     * 删除角色
     */
    @RequestMapping("deleteRole")
    public Result deleteRole(@RequestParam("id") Integer id) {
        try {
            roleService.deleteRole(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    /**
     * 通过角色查询菜单id
     */
    @RequestMapping("findMenuByRoleId")
    public Result findMenuByRoleId(@RequestParam("id") Integer id) {
        try {
            List<Integer> menuIds =  roleService.findMenuByRoleId(id);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    /**
     * 查询所有的角色
     */
    @RequestMapping("findAllRoles")
    public Result findAllRoles() {
        try {
            List<Role> roleList = roleService.findAllRoles();
            return new Result(true, MessageConstant.QUERY_ROLE_LIST_SUCCESS, roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLE_LIST_FAIL);
        }
    }

    /**
     * 通过用户id查询角色
     */
    @RequestMapping("findRoleByUserId")
    public Result findRoleByUserId(@RequestParam("id") Integer userId) {
        try {
            List<Integer> roleIds = roleService.findRoleByUserId(userId);
            return new Result(true, MessageConstant.QUERY_ROLE_LIST_SUCCESS, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLE_LIST_FAIL);
        }
    }

}
