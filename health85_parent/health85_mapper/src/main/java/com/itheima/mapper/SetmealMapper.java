package com.itheima.mapper;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("checkGroupIds") Integer[] checkGroupIds,@Param("id") Integer id);

    List<Setmeal> findByCondition(@Param("queryString") String queryString);

    List<Setmeal> findSetmealAll();

    //Setmeal findById(@Param("id")Integer id);

    Setmeal findSetmealById(@Param("id")Integer id);

    List<Map> getSetmealReport();
}
