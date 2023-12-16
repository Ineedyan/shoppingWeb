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

    // 添加购物记录
    @PostMapping("add")
    public Map<String,Object> addRecordToCart(Cart cart, HttpSession session){
        return cartService.addRecordToCart(cart, session);
    }

    // 显示购物车信息
    @PostMapping("show")
    public List<detailCart> showRecordFromCart(HttpSession session){
        return cartService.showRecordFromCart(session);
    }

    // 删除购物车记录
    @PostMapping("delete")
    public Map<String,Object> deleteRecordByID(Cart cart){
        return cartService.deleteRecordByID(cart);
    }

    // 清空购物车
    @PostMapping("clear")
    public Map<String,Object> clearCart(HttpSession session){
        return cartService.clearCart(session);
    }
}
