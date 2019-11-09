package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.mapper.UserMapper;
import pers.yang.pojo.User;
import pers.yang.service.UserService;

/**
 * 用户
 * @author yf
 * @date 2019/11/7
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


   /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
