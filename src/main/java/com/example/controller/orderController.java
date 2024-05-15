package com.example.controller;

import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import com.example.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class orderController {
    @Resource
    private OrderService orderService;

    /**
     * 创建订单接口
     * @param order 订单信息
     * @param session 用户session
     * @return 创建订单结果
     */
    @PostMapping("create")
    public Map<String, Object> createOrder(Order order, HttpSession session){
        return orderService.createOrder(order, session);
    }

    /**
     * 根据用户id查询订单
     * @param session 用户session
     * @return 查询结果
     */
    @PostMapping("getInfo")
    public List<Order> searchOrderByID(HttpSession session){
        return orderService.searchOrderByID(session);
    }

    /**
     * 查询订单详情接口
     * @param orderId 订单id
     * @return 订单详情
     */
    @PostMapping("getOrderDetails")
    public List<OrderItem> getOrderDetails(Integer orderId){
        return orderService.getOrderDetails(orderId);
    }

    /**
     * 获取所有订单 接口
     * @return 所有订单信息
     */
    @PostMapping("getAllOrder")
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    /**
     * 根据销售人员获取订单接口
     * @param session 销售人员的session信息
     * @return 订单信息
     */
    @PostMapping("getOrdersBySalesman")
    public List<Order> getOrdersBySalesman(HttpSession session){
        return orderService.getOrdersBySalesman(session);
    }

    /**
     * 根据指定对象获取订单接口
     * @param object 获取对象
     * @return 订单信息
     */
    @PostMapping("getOrdersByObject")
    public List<Order> getOrdersByObject(@RequestParam(value = "object") String object){
        return orderService.getOrdersByObject(object);
    }

    /**
     * 订单发货接口
     * @param orderId 订单ID
     * @param expressNumber 快递单号
     * @return 发货结果
     */
    @PostMapping("delivery")
    public Map<String, Object> delivery(@RequestParam("orderId") Integer orderId, @RequestParam("expressNumber") String expressNumber){
        return orderService.delivery(orderId, expressNumber);
    }

    /**
     * 终止订单接口
     * @param orderId 订单ID
     * @return 终止结果
     */
    @PostMapping("endOrder")
    public Map<String, Object> endOrder(@RequestParam("orderId") Integer orderId){
        return orderService.endOrder(orderId);
    }

    /**
     * 删除订单 接口
     * @param orderId 订单id
     * @return 删除结果
     */
    @PostMapping("deleteOrder")
    public Map<String, Object> deleteOrder(@RequestParam("orderId") Integer orderId){
        return orderService.deleteOrder(orderId);
    }

}
