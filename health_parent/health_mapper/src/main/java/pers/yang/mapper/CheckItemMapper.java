package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.CheckItem;

import java.util.List;

/**
 * 检查项
 * @author yf
 * @date 2019/10/27
 */
public interface CheckItemMapper {
    void add(CheckItem checkItem);

    List<CheckItem> findCheckItemByCondition(@Param("queryString") String queryString);

    void deleteCheckItemById(@Param("id") Integer id);

    CheckItem findCheckItemById(@Param("id") Integer id);

    void updateCheckItem(CheckItem checkItem);

    List<CheckItem> findAllCheckItems();

    Integer[] findCheckItemByCheckGroupId(@Param("id") Integer id);
}
