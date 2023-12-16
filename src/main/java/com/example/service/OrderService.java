package com.example.service;

import com.example.mapper.AddressMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import com.example.pojo.User;
import com.example.pojo.detailCart;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    private CartService cartService;
    @Resource
    private EmailService emailService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;

    // 创建订单
    public Map<String, Object> createOrder(Order order, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取用户ID
        Integer useID = (Integer) session.getAttribute("userID");
        if(useID == null){
            resultMap.put("code", 400);
            resultMap.put("message","操作失败！未登录或长时间未操作！请重新登录");
        }
        // 设置订单时间
        order.setOrderTime(LocalDateTime.now());
        // 设置用户ID
        order.setUserId(useID);
        List<detailCart> detailCartList = cartService.showRecordFromCart(session);
        // 设置地址
        String fullAddress = addressMapper.getFullAddressInfoByID(order.getAddress());
        order.setAddress(fullAddress);
        // 计算订单价格总和
        double totalPriceSum = detailCartList.stream()
                .mapToDouble(item -> item.getFinalPrice() * item.getAmount())
                .sum();
        order.setTotalPrice(totalPriceSum);
        // 创建订单
        int result1 = orderMapper.createOrder(order);
        // 储存订单具体信息
        for(detailCart cartItem:detailCartList){
            OrderItem orderItem = new OrderItem();
            // 设置订单中具体的商品信息
            orderItem.setOrderId(order.getId());
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setTotalPrice(cartItem.getAmount()* cartItem.getFinalPrice());
            orderItem.setTitle(cartItem.getTitle());
            int result2 = orderMapper.recordOrder(orderItem);
            if(result2 <=0){
                resultMap.put("code", 400);
                resultMap.put("message","下单失败！");
            }
        }

        if(result1 > 0){
            resultMap.put("code", 200);
            resultMap.put("message","下单成功！已发送邮件通知用户！");
            // 发送下单成功的邮件
            User user = userMapper.getUserInfo(useID);
            emailService.sendMailForBuy(user.getEmail());
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","下单失败！");
        }
        return resultMap;
    }

    // 根据用户ID查询订单
    public List<Order> searchOrderByID(HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        return orderMapper.searchOrderByID(userID);
    }

    // 查询订单详情
    public List<OrderItem> getOrderDetails(Integer orderId) {
        return orderMapper.getOrderDetails(orderId);
    }

    // 获取所有订单
    public List<Order> getAllOrder() {
        return orderMapper.getAllOrder();
    }
}
