package pers.yang.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yang.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组
 * @author yf
 * @date 2019/10/29
 */
public interface CheckGroupMapper {
    /**
     * 添加检查组
     * @param checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 添加检查组和检查项关联数据
     * @param checkitemIds
     * @param id
     */
    void addCheckGroupAndCheckItem(@Param("checkitemIds") Integer[] checkitemIds, @Param("id") Integer id);

    /**
     * 通过条件分页查询检查组
     * @param queryString
     * @return
     */
    List<CheckGroup> findCheckGroupByCondition(@Param("queryString") String queryString);

    /**
     * 更新检查组信息
     * @param checkGroup
     */
    void updateCheckGroup(CheckGroup checkGroup);

    /**
     * 删除检查组和检查项中间表关系
     * @param id
     */
    void deleteCheckGroupAndCheckItem(@Param("id") Integer id);

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteCheckGroupById(@Param("id") Integer id);

    /**
     * 通过检查组id删除检查组和检查项关联关系
     * @param id
     */
    void deleteAssociation(@Param("id") Integer id);

    /**
     * 查询所有的检查组信息
     * @return
     */
    List<CheckGroup> findAllCheckGroups();

}
