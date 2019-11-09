package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.entity.Result;
import pers.yang.pojo.Setmeal;
import pers.yang.service.SetmealService;

import java.util.List;

/**
 * @author yf
 * @date 2019/11/2
 */
@RestController
@RequestMapping("setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 查询套餐列表
     * @return Result结果
     */
    @RequestMapping("findAllSetmeals")
    public Result findAllSetmeals() {
        try {
            List<Setmeal> setmealList = setmealService.findAllSetmeals();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 通过id查询套餐详情
     */
    @RequestMapping("findSetmealDetailsById")
    public Result findSetmealDetailsById(@RequestParam("id") Integer id) {
        try {
            Setmeal setmeal = setmealService.findSetmealDetailsById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 通过id查询套餐
     */
    @RequestMapping("findSetmealById")
    public Result findSetmealById(@RequestParam("id") Integer id) {
        try {
            Setmeal setmeal = setmealService.findSetmealById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
