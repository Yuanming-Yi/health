package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.entity.PageResult;
import pers.yang.mapper.CheckItemMapper;
import pers.yang.pojo.CheckItem;
import pers.yang.service.CheckItemService;

import java.util.List;

/**
 * 检查项
 * @author yf
 * @date 2019/10/27
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    /**
     * 分页查询检查项
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findCheckItemByCondition(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<CheckItem> list = checkItemMapper.findCheckItemByCondition(queryString);
        PageInfo pageInfo = new PageInfo(list);
        PageResult result = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return result;
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteCheckItemById(Integer id) {
        checkItemMapper.deleteCheckItemById(id);
    }

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    @Override
    public CheckItem findCheckItemById(Integer id) {
        CheckItem checkItem =  checkItemMapper.findCheckItemById(id);
        return checkItem;
    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkItemMapper.updateCheckItem(checkItem);
    }

    /**
     * 查询所有的检查项
     * @return
     */
    @Override
    public List<CheckItem> findAllCheckItems() {
        return checkItemMapper.findAllCheckItems();
    }

    /**
     * 查询指定检查组下的检查项
     * @param id
     * @return
     */
    @Override
    public Integer[] findCheckItemByCheckGroupId(Integer id) {
        return checkItemMapper.findCheckItemByCheckGroupId(id);
    }
}
