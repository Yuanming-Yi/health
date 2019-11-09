package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){

        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

    }

    /**
     * 分页查询
     */

    @RequestMapping("findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        try {
            PageResult pageResult = checkItemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    /**
     * 通过id删除检查项
     */
    @RequestMapping("deleteById")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    //@PreAuthorize("('ROLE_HEALTH_MANAGER')")
    public Result deleteById(@RequestParam("id")Integer id){

        try {
            checkItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }  catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }

    }

    /**
     * 通过id查询数据
     */
    @RequestMapping("findById")
    public Result findById(@RequestParam("id")Integer id){

        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
    /**
     * 编辑更新
     */
    @RequestMapping("edit")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem){

        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }

    /**
     * 查询所有检查项信息
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll(){

        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    /**
     * 通过检查组id查询关联的检查项的集合
     */
    @RequestMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id")Integer id){

        try {
            List<Integer> ids = checkItemService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }


}
