package pers.yang.security;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pers.yang.pojo.Permission;
import pers.yang.pojo.Role;
import pers.yang.pojo.User;
import pers.yang.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yf
 * @date 2019/11/7
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 通过用户名查询用户信息
        User user = userService.findUserByUsername(username);

        // 定义集合封装权限
        List<GrantedAuthority> list = new ArrayList<>();
        // 获取角色
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            // 添加角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                // 添加权限
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }
}
