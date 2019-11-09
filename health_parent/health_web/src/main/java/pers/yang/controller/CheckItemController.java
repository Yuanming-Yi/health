package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.entity.Result;
import pers.yang.pojo.CheckItem;
import pers.yang.service.CheckItemService;

import java.util.List;

/**
 * 检查项controlelr
 * @author yf
 * @date 2019/10/27
 */
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
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 按照条件分页查询检查项
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findCheckItemByCondition")
    public Result findCheckItemByCondition(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult result = checkItemService.findCheckItemByCondition(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id删除检查项
     */
    @RequestMapping("deleteCheckItemById")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result deleteCheckItemById(@RequestParam("id") Integer id) {
        try {
            checkItemService.deleteCheckItemById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id查询检查项
     */
    @RequestMapping("findCheckItemById")
    public Result findCheckItemById(@RequestParam("id") Integer id) {
        try {
            CheckItem checkItem =  checkItemService.findCheckItemById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 更新检查项
     */
    @RequestMapping("updateCheckItem")
    public Result upateCheckItem(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.updateCheckItem(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询所有的检查项
     */
    @RequestMapping("findAllCheckItems")
    public Result findAllCheckItems() {
        try {
            List<CheckItem> checkItemList = checkItemService.findAllCheckItems();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询指定检查组下的检查项
     */
    @RequestMapping("findCheckItemByCheckGroupId")
    public Result findCheckItemByCheckGroupId(@RequestParam("id") Integer id) {
        try {
            Integer[] ids = checkItemService.findCheckItemByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}
