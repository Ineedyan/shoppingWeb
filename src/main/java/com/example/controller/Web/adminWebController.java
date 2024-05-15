package com.example.controller.Web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class adminWebController {
    @RequestMapping("adminWeb/UsersManagement")
    public String userInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/UserManagement";
    }

    @RequestMapping("adminWeb/SalesmenManagement")
    public String salesmanInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/SalesmanManagement";
    }

    @RequestMapping("adminWeb/LogsManagement")
    public String logsInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/LogsManagement";
    }

    @RequestMapping("adminWeb/OrdersManagement")
    public String ordersInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/OrderManagement";
    }

    @RequestMapping("adminWeb/GoodsManagement")
    public String goodsInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/GoodsManagement";
    }

    @RequestMapping("adminWeb/DataManagement")
    public String dataInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/DataManagement";
    }

    @RequestMapping("adminWeb/SaleDataManagement")
    public String saleDataInfo(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/adminLogin";
        }
        return "adminWeb/SaleDataManagement";
    }
}
