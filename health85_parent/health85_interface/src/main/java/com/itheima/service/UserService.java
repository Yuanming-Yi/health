package com.itheima.service;

import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findByUsername(String username);

    List<Map<String,Object>> findMenuByUsername(String username);
}
