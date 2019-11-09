package pers.yang.service;

import pers.yang.entity.PageResult;
import pers.yang.pojo.CheckItem;

import java.util.List;

/**
 * 检查项
 * @author yf
 * @date 2019/10/27
 */
public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findCheckItemByCondition(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteCheckItemById(Integer id);

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    CheckItem findCheckItemById(Integer id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void updateCheckItem(CheckItem checkItem);

    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAllCheckItems();

    /**
     * 查询指定检查组下的检查项
     * @param id
     * @return
     */
    Integer[] findCheckItemByCheckGroupId(Integer id);

}
