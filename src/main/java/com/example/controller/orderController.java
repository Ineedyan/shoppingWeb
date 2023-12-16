package com.example.controller;

import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import com.example.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class orderController {
    @Resource
    private OrderService orderService;

    // 创建订单
    @PostMapping("create")
    public Map<String, Object> createOrder(Order order, HttpSession session){
        return orderService.createOrder(order, session);
    }

    // 查询订单（根据用户ID）
    @PostMapping("getInfo")
    public List<Order> searchOrderByID(HttpSession session){
        return orderService.searchOrderByID(session);
    }

    // 查询订单详情
    @PostMapping("getOrderDetails")
    public List<OrderItem> getOrderDetails(Integer orderId){
        return orderService.getOrderDetails(orderId);
    }

    //
    @PostMapping("getAllInfo")
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

}
