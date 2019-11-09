package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.entity.Result;
import pers.yang.service.UserService;

import java.security.Principal;

/**
 * 会员
 * @author yf
 * @date 2019/11/7
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("getUsername")
    public Result getUsername(Principal principal) {
        try {
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 分页查询会员信息
     */
    // @RequestMapping("findUserBypage")
    // public Result findUserByPage(@RequestBody QueryPageBean queryPageBean) {
    //     List<User> userList = userService.findUserByPage(queryPageBean);
    //     return new Result(true, MessageConstant.queryuser)
    // }

}
