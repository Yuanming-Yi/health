package com.itheima.mapper;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {
    void add(CheckItem checkItem);

    /**
     * 只要参数不是POJO或者Map类型，可以全部使用
     *
     * @param queryString
     * @return
     */
    List<CheckItem> findByCondition(@Param("queryString")String queryString);

    Integer findCountByCheckItemId(@Param("id")Integer id);

    void deleteById(@Param("id")Integer id);

    CheckItem findById(@Param("id")Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id")Integer id);

    List<CheckItem> findCheckItemByCheckGroupId(@Param("id")Integer id);
}
