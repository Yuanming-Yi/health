package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 套餐
 * @author yf
 * @date 2019/10/31
 */
public interface SetmealMapper {
    /**
     * 添加套餐
     * @param setmeal
     */
    void addSetmeal(Setmeal setmeal);

    void addAssoication(@Param("id") Integer id, @Param("checkgroupIds") Integer[] checkgroupIds);

    /**
     * 分页查询套餐数据
     * @param queryString
     * @return
     */
    List<Setmeal> findSetmealByCondition(@Param("queryString") String queryString);

    /**
     * 查询套餐列表
     * @return
     */
    List<Setmeal> findAllSetmeals();

    /**
     * 通过id查询套餐详情
     * @param id
     * @return
     */
    Setmeal findSetmealDetailsById(@Param("id") Integer id);

    /**
     * 通过id查询套餐
     * @param id
     * @return
     */
    Setmeal findSetmealById(@Param("id") Integer id);

    /**
     * 查询预约统计数据
     * @return
     */
    List<Map> getSetmealReport();
}
