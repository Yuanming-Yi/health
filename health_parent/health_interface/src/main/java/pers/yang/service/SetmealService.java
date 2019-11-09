package pers.yang.service;

import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 套餐
 * @author yf
 * @date 2019/10/31
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal
     */
    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询套餐数据
     * @param queryPageBean
     * @return
     */
    PageResult findSetmealByCondition(QueryPageBean queryPageBean);

    /**
     * 查询套餐列表
     * @return
     */
    List<Setmeal> findAllSetmeals();

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    Setmeal findSetmealDetailsById(Integer id);

    /**
     * 通过id查询套餐
     * @param id
     * @return
     */
    Setmeal findSetmealById(Integer id);

    /**
     * 查询预约占比
     * @return
     */
    Map getSetmealReport();
}
