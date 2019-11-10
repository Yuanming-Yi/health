package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MenuMapper;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 菜单
 * @author yf
 * @date 2019/11/9
 */
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 通过级别查询菜单
     * @param
     * @return
     */
    @Override
    public List<Map> findMenuList(Integer id, Integer level) {
        List<Map> menuMap = menuMapper.findMenuList(id, level);
        if (menuMap != null && menuMap.size() > 0) {
            for (Map map : menuMap) {
                List<Map> children = findMenuList((Integer) map.get("id"), level + 1);
                map.put("children", children);
            }
        }
        return menuMap;
    }

    /**
     * 添加菜单
     * @param menu
     */
    @Override
    public void addMenu(Menu menu) {
        // 获取path
        String path = menu.getPath();
        if (path != null && !path.equals("")) {
            if (path.startsWith("/")) {
                Integer maxPath = menuMapper.findMaxPathNumber(path.substring(0, path.length() - 2));
                menu.setPath(path.substring(0, path.length() - 2) +"-"+String.valueOf(maxPath+1));
            } else {
                Integer maxPath = menuMapper.findMaxPathNumber("/" + path + "-");
                if (maxPath == null) {
                    maxPath = 0;
                }
                menu.setPath("/"+path +"-"+String.valueOf(maxPath+1));
            }
            menu.setLevel(menu.getLevel() + 1);
        } else {
            Integer maxPath = menuMapper.findMaxPathNumber(null);
            menu.setPath(String.valueOf(maxPath+1));
            menu.setLevel(1);
        }


        menuMapper.addMenu(menu);
    }

    /**
     * 通过id查询菜单
     * @param id
     * @return
     */
    @Override
    public Menu findMenuById(Integer id) {
        return menuMapper.findMenuById(id);
    }

    /**
     * 编辑菜单
     * @param menu
     */
    @Override
    public void editMenu(Menu menu) {
        menuMapper.editMenu(menu);
    }
    /**
     * 删除菜单
     */
    @Override
    public void deleteMenu(Menu menu) {
        // 判断菜单是否被引用
        Integer menuUsedCount = menuMapper.findMenuCountUsedByUser(menu.getId());
        if (menuUsedCount > 0) {
            throw new RuntimeException("此菜单被引用，无法删除！");
        }
        // 删除子菜单
        List<Menu> children = menu.getChildren();
        if (children != null && children.size() > 0) {
            for (Menu child : children) {
                menuMapper.deleteMenu(child.getId());
            }
        }
        // 删除菜单
        menuMapper.deleteMenu(menu.getId());
    }
}
