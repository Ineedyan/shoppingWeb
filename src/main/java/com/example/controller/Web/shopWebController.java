package com.example.controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class shopWebController {
    // 购物网站页面跳转
    @RequestMapping("shoppingWeb")
    public String shoppingWeb() {
        return "userWeb/shoppingWeb";
    }

    // 地址管理页面跳转
    @RequestMapping("addressManagement")
    public String addressManagement(){
        return "userWeb/addressManagement";
    }

    // 购物车页面跳转
    @RequestMapping("cart")
    public String cart(){
        return "userWeb/cart";
    }

    // 订单页面跳转
    @RequestMapping("myOrder")
    public String myOrder(){ return "userWeb/myOrder";}

}
