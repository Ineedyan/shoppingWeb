package com.example.mapper;

import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {

    // 创建订单
    @Insert("INSERT INTO `order`(userId, orderTime, totalPrice, address) " +
            "VALUES (#{userId},#{orderTime},#{totalPrice},#{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createOrder(Order order);

    // 储存订单信息
    @Insert("INSERT INTO orderitem(orderId, title, amount, totalPrice) " +
            "VALUES (#{orderId},#{title},#{amount},#{totalPrice})")
    int recordOrder(OrderItem orderItem);

    // 查询订单信息
    @Select("SELECT * FROM `order` WHERE userId=#{userID}")
    List<Order> searchOrderByID(Integer userID);

    // 查询订单详情
    @Select("SELECT * FROM orderitem WHERE orderId=#{orderId}")
    List<OrderItem> getOrderDetails(Integer orderId);

    // 获取所有订单
    @Select("SELECT * FROM `order`")
    List<Order> getAllOrder();
}
