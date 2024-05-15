package com.example.controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class accountActionController {
    //  登录页面跳转
    @RequestMapping("login")
    public String login()
    {
        return "loginWeb/login";
    }

    //  注册页面跳转
    @RequestMapping("register")
    public String register()
    {
        return "loginWeb/register";
    }

    // 开发者登录页面跳转
    @RequestMapping("adminLogin")
    public String adminLogin(){
        return "loginWeb/adminLoginWeb";
    }

    // 销售人员登录页面跳转
    @RequestMapping("salesmanLogin")
    public String salesmanLogin(){ return "loginWeb/salesmanLoginWeb";}

}
