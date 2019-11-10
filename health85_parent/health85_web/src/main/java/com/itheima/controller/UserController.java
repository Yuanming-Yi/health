package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("getUsername")
    public Result getUsername(Principal principal){

        try {
            String name = principal.getName();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("findMenuByUsername")
    public Result findMenuByUsername(@RequestParam String username){
        try {
            List<Map<String,Object>> menulist = userService.findMenuByUsername(username);
            return new Result(true,MessageConstant.GET_MENU_SUCCESS,menulist);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MENU_FAIL);
        }
    }

    /**
     * 分页查询会员信息
     */
    @RequestMapping("findUserByPage")
    public Result findUserByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = userService.findUserByPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_USER_LIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_LIST_FAIL);
        }
    }

    /**
     * 添加用户
     */
    @RequestMapping("addUser")
    public Result addUser(@RequestBody ModelMap map) {
        try {
            String userJson = JSON.toJSONString(map.get("user"));
            String roleIdsJson = JSON.toJSONString(map.get("roleIds"));
            User user = JSON.parseObject(userJson, User.class);
            Integer[] roleIds = JSON.parseObject(roleIdsJson, Integer[].class);
            String password = passwordEncoder.encode("1234");
            user.setPassword(password);
            userService.addUser(user, roleIds);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    /**
     * 编辑用户
     */
    @RequestMapping("editUser")
    public Result editUser(@RequestBody ModelMap map) {
        try {
            String userJson = JSON.toJSONString(map.get("user"));
            String roleIdsJson = JSON.toJSONString(map.get("roleIds"));
            User user = JSON.parseObject(userJson, User.class);
            Integer[] roleIds = JSON.parseObject(roleIdsJson, Integer[].class);
            String password = passwordEncoder.encode("1234");
            user.setPassword(password);
            userService.editUser(user, roleIds);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_USER_FAIL);
        }
    }

    /**
     * 删除用户
     */
    @RequestMapping("deleteUser")
    public Result deleteUser(@RequestParam("id") Integer id) {
        try {
            userService.deleteUser(id);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_USER_FAIL);
        }
    }

    /**
     * 查询用户名
     */
    @RequestMapping("findUsername")
    public Result findUsername(@RequestParam("username") String username) {
        User user = userService.findUsername(username.trim());
        if (user != null) {
            return new Result(false, "用户名重复，请更换！");
        }
        return new Result(true, "用户名可用！");
    }

}
