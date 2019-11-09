package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {

        //新增检查组
        checkGroupMapper.add(checkGroup);

        //添加检查组和检查项的关系
        setCheckGroupAndCheckItem(checkItemIds,checkGroup.getId());

    }

    private void setCheckGroupAndCheckItem(Integer[] checkItemIds, Integer id) {

        //先判断checkItemIds有数据
        if (checkItemIds!=null && checkItemIds.length>0){

            //插入中间表数据
            checkGroupMapper.setCheckGroupAndCheckItem(checkItemIds,id);

        }

    }

/*
    private void setCheckGroupAndCheckItem(Integer[] checkItemIds, Integer id) {

        //先判断checkItemIds有数据
        if (checkItemIds!=null && checkItemIds.length>0){

            //循环遍历每一个检查项的id,去插入中间表数据
            for (Integer checkItemId : checkItemIds) {
                //插入中间表数据
                checkGroupMapper.setCheckGroupAndCheckItem(checkItemId,id);
            }

        }

    }*/

    /**
     * 分页查询检查组
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
   /* @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        //定义limit x,y中的x的值
        int firstResult = (currentPage-1)*pageSize;

        //查询总记录数
        Long count = checkGroupMapper.findCount(queryString);

        //查询 分页数据
        List<CheckGroup> checkGroupList = checkGroupMapper.findByCondition(firstResult,pageSize,queryString);

        PageResult pageResult = new PageResult(count, checkGroupList);

        return pageResult;
    }*/

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);

        //条件查询数据
        List<CheckGroup> checkGroupList = checkGroupMapper.findByCondition(queryString);

        PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());


    }

    /**
     * 通过id查询数据
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupMapper.findById(id);
    }

    /**
     * 编辑更新
     * @param checkItemIds
     * @param checkGroup
     */
    @Override
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {

        //删除检查组和检查项的中间表数据
        checkGroupMapper.deleteAssociation(checkGroup.getId());

        //重新插入中间表数据
        setCheckGroupAndCheckItem(checkItemIds,checkGroup.getId());

        //检查组的数据进行更新
        checkGroupMapper.edit(checkGroup);

    }

    @Override
    public void deleteById(Integer id) {

        //判断有没有被套餐引用
        Integer count = checkGroupMapper.findCountById(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.CHECKGROUP_IS_QUOTED);
        }
        //删除检查项和检查组的中间表关系
        checkGroupMapper.deleteAssociation(id);
        //删除检查组
        checkGroupMapper.deleteById(id);

    }

    /**
     * 查询所有检查组数据
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll();
    }
}
