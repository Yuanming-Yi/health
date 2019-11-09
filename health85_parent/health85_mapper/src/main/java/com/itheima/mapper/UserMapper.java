package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User findByUsername(@Param("username") String username);

    List<Map<String,Object>> findFirstMenuByUsername(@Param("username") String username);

    List<Map<String,Object>> findSecondMenuByUsernameAndPath(@Param("username") String username, @Param("path") String path);
}
