package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;


    /**
     * 新增
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        //定义页面大小和当前页的页面
        PageHelper.startPage(currentPage,pageSize);
        //查询分页数据
        List<CheckItem> checkItemList = checkItemMapper.findByCondition(queryString);
        //进行包装
        PageInfo<CheckItem> pageInfo = new PageInfo<>(checkItemList);

        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());

        return pageResult;
    }

    /**
     * 通过id删除检查项
     */

    @Override
    public void deleteById(Integer id) {

        //判断当前检查项是否被引用
        Integer count = checkItemMapper.findCountByCheckItemId(id);
        if (count>0){
            throw  new RuntimeException(MessageConstant.CHECKITEM_IS_QUOTED);
        }
        //执行删除
        checkItemMapper.deleteById(id);
    }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkItemMapper.findById(id);
    }

    /**
     * 编辑更新
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.edit(checkItem);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkItemMapper.findCheckItemIdsByCheckGroupId(id);
    }
}
