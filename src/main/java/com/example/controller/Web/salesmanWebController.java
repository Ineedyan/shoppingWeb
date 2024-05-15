package com.example.controller.Web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class salesmanWebController {
    @RequestMapping("salesmanWeb/kindsManagement")
    public String salesmanWeb(HttpSession session){
        if(session.getAttribute("salesmanID") == null){
            return "redirect:/salesmanLogin";
        }
        return "salesmanWeb/KindsManagement";
    }

    @RequestMapping("salesmanWeb/goodsManagement")
    public String GoodsInfo(HttpSession session){
        if(session.getAttribute("salesmanID") == null){
            return "redirect:/salesmanLogin";
        }
        return "salesmanWeb/GoodsManagement";
    }

    @RequestMapping("salesmanWeb/ordersManagement")
    public String OrderInfo(HttpSession session){
        if(session.getAttribute("salesmanID") == null){
            return "redirect:/salesmanLogin";
        }
        return "salesmanWeb/OrdersManagement";
    }

    @RequestMapping("salesmanWeb/saleDataManagement")
    public String DataInfo(HttpSession session){
        if(session.getAttribute("salesmanID") == null){
            return "redirect:/salesmanLogin";
        }
        return "salesmanWeb/SaleDataManagement";
    }


}
