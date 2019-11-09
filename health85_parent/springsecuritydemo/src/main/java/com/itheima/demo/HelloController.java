package com.itheima.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hello")
public class HelloController {

    @RequestMapping("demo1")
    public String demo1(){
        System.out.println("demo1方法执行了");
        return "forward:/a.html";
    }

    @RequestMapping("demo2")
    public String demo2(){
        System.out.println("demo1方法执行了");
        return "redirect:/a.html";
    }

    @RequestMapping("demo3")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String demo3(){
        System.out.println("demo3方法执行了");
        return "redirect:/a.html";
    }

    @RequestMapping("demo4")
    @PreAuthorize("hasAuthority('add')")
    public String demo4(){
        System.out.println("demo4方法执行了");
        return "redirect:/a.html";
    }
}
