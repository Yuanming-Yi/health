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
import pers.yang.pojo.CheckGroup;
import pers.yang.service.CheckGroupService;

import java.util.List;

/**
 * 检查组
 * @author yf
 * @date 2019/10/29
 */
@RestController
@RequestMapping("checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     */
    @RequestMapping("addCheckGroup")
    public Result addCheckGroup(@RequestParam("checkitemIds") Integer[] checkitemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.addCheckGroup(checkitemIds, checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询检查组
     */
    @RequestMapping("findCheckGroupByCondition")
    public Result findCheckGroupByCondition(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkGroupService.findCheckGroupByCondition(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 更新检查组信息
     */
    @RequestMapping("updateCheckGroup")
    public Result updateCheckGroup(@RequestBody CheckGroup checkGroup, @RequestParam("checkItemIds") Integer[] checkItemIds) {
        try {
            checkGroupService.updateCheckGroup(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 通过id删除检查组
     */
    @RequestMapping("deleteCheckGroupById")
    public Result deleteCheckGroupById(@RequestParam("id") Integer id) {
        try {
            checkGroupService.deleteCheckGroupById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有的检查组
     */
    @RequestMapping("findAllCheckGroups")
    public Result findAllCheckGroups() {
        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAllCheckGroups();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
        }
    }

}
