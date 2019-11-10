package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author yf
 * @date 2019/11/9
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @RequestMapping("findMenuList")
    public Result findMenuList() {
        try {
            List<Map> menuMap = menuService.findMenuList(null, 1);
            String json = JSON.toJSONString(menuMap);
            System.out.println(json);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    @RequestMapping("addMenu")
    public Result addMenu(@RequestBody Menu menu) {
        try {
            menuService.addMenu(menu);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
    }

    @RequestMapping("findMenuById")
    public Result findMenuById(@RequestParam("id") Integer id) {
        try {
            Menu menu = menuService.findMenuById(id);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_SUCCESS);
        }

    }

    @RequestMapping("editMenu")
    public  Result editMenu(@RequestBody Menu menu){
        try {
            menuService.editMenu(menu);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

    @RequestMapping("deleteMenu")
    public Result deleteMenu(@RequestBody Menu menu) {
        try {
            menuService.deleteMenu(menu);
            return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
    }
}