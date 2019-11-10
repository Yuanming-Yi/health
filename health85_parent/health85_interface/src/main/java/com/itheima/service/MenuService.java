package com.itheima.service;

import com.itheima.pojo.Menu;


import java.util.List;
import java.util.Map;

/**
 * 菜单
 * @author yf
 * @date 2019/11/9
 */
public interface MenuService {
    /**
     * 通过级别查询菜单
     * @param
     * @return
     */
    List<Map> findMenuList(Integer id, Integer level);

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
    Menu findMenuById(Integer id);

    /**
     * 编辑菜单
     * @param menu
     */
    void editMenu(Menu menu);

    /**
     * 删除菜单
     * @param menu
     */
    void deleteMenu(Menu menu);
}
