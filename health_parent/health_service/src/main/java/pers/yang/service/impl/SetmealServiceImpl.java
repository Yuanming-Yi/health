package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.constant.RedisConstant;
import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.mapper.SetmealMapper;
import pers.yang.pojo.Setmeal;
import pers.yang.service.SetmealService;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yf
 * @date 2019/10/31
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐信息
        setmealMapper.addSetmeal(setmeal);
        // 添加套餐和检查组关联信息
        setmealMapper.addAssoication(setmeal.getId(), checkgroupIds);

        if (setmeal.getImg() != null) {
            // 图片不为空, 将图片保存到redis的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        }
    }

    /**
     * 分页查询套餐数据
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findSetmealByCondition(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Setmeal> setmealList = setmealMapper.findSetmealByCondition(queryPageBean.getQueryString());
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setmealList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询套餐列表
     * @return
     */
    @Override
    public List<Setmeal> findAllSetmeals() {
        return setmealMapper.findAllSetmeals();
    }

    /**
     * 通过id查询套餐详情
     * @param id
     * @return
     */
    @Override
    public Setmeal findSetmealDetailsById(Integer id) {
        return setmealMapper.findSetmealDetailsById(id);
    }

    /**
     * 通过id查询套餐
     * @param id
     * @return
     */
    @Override
    public Setmeal findSetmealById(Integer id) {
        return setmealMapper.findSetmealById(id);
    }

    /**
     * 查询预约占比
     * @return
     */
    @Override
    public Map getSetmealReport() {
        List<Map> list = setmealMapper.getSetmealReport();

        List<String> setmealNames = new ArrayList<>();

        for (Map map : list) {
            String name = (String) map.get("name");
            setmealNames.add(name);
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("setmealNames", setmealNames);
        map.put("setmealCounts", list);
        return map;
    }
}
