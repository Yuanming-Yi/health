package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
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
}
