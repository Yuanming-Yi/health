package com.itheima.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Map map = new HashMap<>();

    public void init(){
        User user1 = new User();
        user1.setUsername("zhangsan");
        user1.setPassword(passwordEncoder.encode("1234"));

        User user2 = new User();
        user2.setUsername("lisi");
        user2.setPassword(passwordEncoder.encode("1234"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //初始化，模拟将map作为数据库来使用
        init();

        //模拟通过用户名从数据库中查询用户
        User user = (User) map.get(username);

        //定义一个集合，封装各种角色和权限
        List<GrantedAuthority> list = new ArrayList<>();

        if (user.getUsername().equals("zhangsan")){

            /* *  大小的单词，就将其作为赋予角色来使用
             *  小写的单击，就将其作为赋予操作权限来使用
             *
             * */
            list.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            list.add(new SimpleGrantedAuthority("add"));

        }

        //配置密码为明文
        //String password = "{noop}"+user.getPassword();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);
    }

  /*  public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode1 = encoder.encode("1234");
        String encode2 = encoder.encode("1234");
        System.out.println(encode1);
        System.out.println(encode2);

        boolean flag = encoder.matches("1234",encode1);
        boolean flag2 = encoder.matches("1234",encode2);
        System.out.println(flag);
        System.out.println(flag2);

    }*/
}
