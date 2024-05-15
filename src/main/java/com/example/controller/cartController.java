package com.example.controller;

import com.example.pojo.Cart;
import com.example.pojo.detailCart;
import com.example.service.CartService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class cartController {
    @Resource
    private CartService cartService;

    /**
     * 新增购物车记录
     * @param cart 购物车信息
     * @param session 用户session
     * @return 添加结果
     */
    @PostMapping("add")
    public Map<String,Object> addRecordToCart(Cart cart, HttpSession session){
        return cartService.addRecordToCart(cart, session);
    }

    /**
     * 根据用户获取购物车记录
     * @param session 用户session
     * @return 购物车记录列表
     */
    @PostMapping("show")
    public List<detailCart> showRecordFromCart(HttpSession session){
        return cartService.showRecordFromCart(session);
    }

    /**
     * 根据购物车id删除记录
     * @param cart 购物车id
     * @return 删除结果
     */
    @PostMapping("delete")
    public Map<String,Object> deleteRecordByID(Cart cart){
        return cartService.deleteRecordByID(cart);
    }

    /**
     * 根据用户id清空购物车
     * @param session 用户session
     * @return 清空结果
     */
    @PostMapping("clear")
    public Map<String,Object> clearCart(HttpSession session){
        return cartService.clearCart(session);
    }
}
