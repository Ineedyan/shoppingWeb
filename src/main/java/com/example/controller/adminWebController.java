package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class adminWebController {
    @RequestMapping("adminWeb")
    public String mainWeb(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb";
    }

    @RequestMapping("UserInfo")
    public String userInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "UserInfo";
    }

    @RequestMapping("GoodsInfo")
    public String GoodsInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "GoodsInfo";}

    @RequestMapping("OrderInfo")
    public String OrderInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "OrderInfo";}

}
