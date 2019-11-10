package com.itheima.mapper;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * 菜单
 * @author yf
 * @date 2019/11/9
 */
public interface MenuMapper {
    /**
     * 通过级别查询菜单
     * @param
     * @return
     */
    List<Map> findMenuList(@Param("id") Integer id, @Param("level") Integer level);

    /**
     * 查询最大path
     * @return
     */
    Integer findMaxPathNumber(@Param("pathCondition") String pathCondition);

    /**
     * 添加菜单
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 通过id查询菜单
     * @param id
     * @return
     */
    Menu findMenuById(@Param("id") Integer id);

    /**
     * 编辑菜单
     * @param menu
     */
    void editMenu(Menu menu);

    /**
     * 查询菜单被用户引用数量
     * @param id
     * @return
     */
    Integer findMenuCountUsedByUser(@Param("id") Integer id);

    /**
     * 删除菜单
     * @param id
     */
    void deleteMenu(@Param("id") Integer id);
}
