package pers.yang.service;

import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组
 * @author yf
 * @date 2019/10/29
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkitemIds
     * @param checkGroup
     */
    void addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup);

    /**
     * 通过条件分页查询检查组信息
     * @param queryPageBean
     * @return
     */
    PageResult findCheckGroupByCondition(QueryPageBean queryPageBean);

    /**
     * 更新检查组
     * @param checkItemIds
     * @param checkGroup
     */
    void updateCheckGroup(Integer[] checkItemIds, CheckGroup checkGroup);

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteCheckGroupById(Integer id);

    /**
     * 查询所有的检查组信息
     * @return
     */
    List<CheckGroup> findAllCheckGroups();
}
