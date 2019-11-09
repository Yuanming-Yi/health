package pers.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yang.entity.PageResult;
import pers.yang.entity.QueryPageBean;
import pers.yang.mapper.CheckGroupMapper;
import pers.yang.pojo.CheckGroup;
import pers.yang.service.CheckGroupService;

import java.util.List;

/**
 * @author yf
 * @date 2019/10/29
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    /**
     * 添加检查组信息
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    public void addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup) {
        // 添加检查组
        checkGroupMapper.addCheckGroup(checkGroup);
        // 表关联，添加检查组和检查项的数据
        checkGroupMapper.addCheckGroupAndCheckItem(checkitemIds, checkGroup.getId());
    }

    /**
     * 通过条件分页查询检查组
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findCheckGroupByCondition(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<CheckGroup> checkGroupList = checkGroupMapper.findCheckGroupByCondition(queryPageBean.getQueryString());
        PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 更新检查组
     * @param checkItemIds
     * @param checkGroup
     */
    @Override
    public void updateCheckGroup(Integer[] checkItemIds, CheckGroup checkGroup) {
        // 更新检查组信息
        checkGroupMapper.updateCheckGroup(checkGroup);
        // 删除中间表的信息
        checkGroupMapper.deleteCheckGroupAndCheckItem(checkGroup.getId());
        // 增加中间表的信息
        checkGroupMapper.addCheckGroupAndCheckItem(checkItemIds, checkGroup.getId());
    }

    /**
     * 通过id删除检查组
     * @param id
     */
    @Override
    public void deleteCheckGroupById(Integer id) {
        // 删除检查组和检查项中间表的数据
        checkGroupMapper.deleteAssociation(id);
        // 删除检查组信息
        checkGroupMapper.deleteCheckGroupById(id);
    }

    /**
     * 查询所有检查组信息
     * @return
     */
    @Override
    public List<CheckGroup> findAllCheckGroups() {
        return checkGroupMapper.findAllCheckGroups();
    }
}
