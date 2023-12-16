package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class accountActionController {
    //  登录页面跳转
    @RequestMapping("login")
    public String login()
    {
        return "login";
    }

    //  注册页面跳转
    @RequestMapping("register")
    public String register()
    {
        return "register";
    }

    // 开发者登录页面跳转
    @RequestMapping("adminLogin")
    public String adminLogin(){
        return "adminLoginWeb";
    }

}
