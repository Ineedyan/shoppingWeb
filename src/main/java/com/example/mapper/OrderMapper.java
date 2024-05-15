package com.example.mapper;

import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper {

    /**
     * 创建/新增订单
     * @param order 订单信息
     * @return 创建结果
     */
    @Insert("INSERT INTO `order`(userId, salesmanName, orderTime, totalPrice, address, state, expressNumber) " +
            "VALUES (#{userId},#{salesmanName},#{orderTime},#{totalPrice},#{address}, #{state}, #{expressNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createOrder(Order order);

    /**
     * 存储订单详情
     * @param orderItem 订单详情
     * @return 存储结果
     */
    @Insert("INSERT INTO orderitem(orderId, productId, title, amount, totalPrice) " +
            "VALUES (#{orderId},#{productId},#{title},#{amount},#{totalPrice})")
    int recordOrder(OrderItem orderItem);

    /**
     * 根据用户id查询订单
     * @param userID 用户id
     * @return 订单列表
     */
    @Select("SELECT * FROM `order` WHERE userId=#{userID}")
    List<Order> searchOrderByID(Integer userID);

    /**
     * 查询订单详情
     * @param orderId 订单id
     * @return 订单详情列表
     */
    @Select("SELECT * FROM orderitem WHERE orderId=#{orderId}")
    List<OrderItem> getOrderDetails(Integer orderId);

    /**
     * 获取所有订单
     * @return 所有订单
     */
    @Select("SELECT * FROM `order`")
    List<Order> getAllOrder();


    /**
     * 根据销售人员获取订单
     * @param salesmanName 销售人员的session信息
     * @return 订单列表
     */
    @Select("SELECT * FROM `order` WHERE salesmanName = #{salesmanName}")
    List<Order> getOrdersBySalesman(String salesmanName);

    /**
     * 根据指定对象获取订单
     * @param object 指定对象（商家）
     * @return 订单信息
     */
    @Select("SELECT * FROM `order` WHERE salesmanName = #{object}")
    List<Order> getOrdersByObject(String object);

    /**
     * 订单发货
     * @param orderId 订单ID
     * @param state 订单状态
     * @param expressNumber 快递单号
     * @return 发货结果
     */
    @Update("UPDATE `order` SET expressNumber = #{expressNumber}, state = #{state} WHERE id = #{orderId}")
    int delivery(Integer orderId, String state, String expressNumber);

    /**
     * 修改订单状态
     * @param orderId 订单ID
     * @param state 订单状态
     * @return 修改订单状态结果
     */
    @Update("UPDATE `order` SET state = #{state} WHERE id = #{orderId}" )
    int endOrder(Integer orderId, String state);

    /**
     * 删除订单
     * @param orderId 订单ID
     * @return 删除结果
     */
    @Delete("DELETE FROM `order` WHERE id = #{orderId}")
    int deleteOrder(Integer orderId);

    /**
     * 获取订单状态
     * @param orderId 订单id
     * @return 订单状态
     */
    @Select("SELECT state FROM `order` WHERE id = #{orderId}")
    String getOrderStateByOrderID(Integer orderId);
}
