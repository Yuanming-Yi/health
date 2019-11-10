package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {

        User user = userMapper.findByUsername(username);
        return user;
    }

    //
    @Override
    public List<Map<String, Object>> findMenuByUsername(String username) {
        List<Map<String, Object>> menulist = userMapper.findFirstMenuByUsername(username);
        for (Map<String, Object> map : menulist) {
            List<Map<String,Object>> children = userMapper.findSecondMenuByUsernameAndPath(username,(String)map.get("path"));
            map.put("children",children);
        }
        return menulist;
    }

    /**
     * 分页查询用户列表
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findUserByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<User> userList = userMapper.findUserByPage(queryPageBean.getQueryString());
        PageInfo pageInfo = new PageInfo(userList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加用户
     * @param user
     * @param roleIds
     */
    @Override
    public void addUser(User user, Integer[] roleIds) {
        // 添加用户信息
        userMapper.addUser(user);
        // 添加角色信息
        userMapper.addAssociationOfUserAndRole(user.getId(), roleIds);
    }

    /**
     * 编辑用户
     * @param user
     * @param roleIds
     */
    @Override
    public void editUser(User user, Integer[] roleIds) {
        // 更新用户数据
        userMapper.editUser(user);
        // 删除用户和角色关联数据
        userMapper.deleteAssociationOfUserAndRole(user.getId());
        // 重新添加用户和角色关联数据
        userMapper.addAssociationOfUserAndRole(user.getId(), roleIds);
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteUser(Integer id) {
        // 删除用户和角色关联
        userMapper.deleteAssociationOfUserAndRole(id);
        // 删除用户数据
        userMapper.deleteUser(id);
    }

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findUsername(String username) {
        return userMapper.findUsername(username);
    }

}
