package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(Integer[] checkGroupIds, Setmeal setmeal) {

        //新增套餐
        setmealMapper.add(setmeal);
        //设置套餐和检查组的中间表关系
        setSetmealAndCheckGroup(checkGroupIds,setmeal.getId());

        if (setmeal.getImg()!=null){
            //将保存到数据库中的图片存入到redis的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        }


    }

    private void setSetmealAndCheckGroup(Integer[] checkGroupIds, Integer id) {

        if (checkGroupIds!=null && checkGroupIds.length>0){
            setmealMapper.setSetmealAndCheckGroup(checkGroupIds,id);
        }

    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);

        //条件查询套餐数据
        List<Setmeal> setmealList = setmealMapper.findByCondition(queryString);

        //数据包装
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setmealList);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Setmeal> findSetmealAll() {
        return setmealMapper.findSetmealAll();
    }

    /**
     * 通过id查询setmeal
     * @param id
     * @return
     */
   /* @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }*/

    @Override
    public Setmeal findById(Integer id) {

        Setmeal setmeal = setmealMapper.findSetmealById(id);
        //通过套餐的id查询套餐对应的检查组集合
        List<CheckGroup> checkGroups = checkGroupMapper.findCheckGroupBySetmealId(setmeal.getId());
        //将查询出来的检查组的信息封装到套餐对象中
        setmeal.setCheckGroups(checkGroups);
        //将检查组遍历，通过每一个检查组的id查询对应的检查项
        for (CheckGroup checkGroup : checkGroups) {
            List<CheckItem> checkItems = checkItemMapper.findCheckItemByCheckGroupId(checkGroup.getId());
            //将检查项的集合封装到检查组中
            checkGroup.setCheckItems(checkItems);

        }

        return setmeal;
    }

    /**
     * 查询套餐占比
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

        Map<String, List> map = new HashMap<>();
        map.put("setmealNames",setmealNames);
        map.put("setmealCounts",list);

        return map;
    }
}
