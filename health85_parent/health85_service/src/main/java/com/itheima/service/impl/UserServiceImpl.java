package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
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
}
