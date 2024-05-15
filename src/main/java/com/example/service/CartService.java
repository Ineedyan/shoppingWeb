package com.example.service;

import com.example.mapper.CartMapper;
import com.example.mapper.GoodsMapper;
import com.example.pojo.Cart;
import com.example.pojo.Product;
import com.example.pojo.detailCart;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 添加购物车
     * @param cart 购物车记录
     * @param session 用户session
     * @return 添加结果
     */
    @MyLog(role = "用户", oper = "购物车操作", operType = "添加", mess = "添加商品至购物车")
    public Map<String, Object> addRecordToCart(Cart cart, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        Integer userID = (Integer) session.getAttribute("userID");
        if(userID == null){
            resultMap.put("code", 400);
            resultMap.put("message","未登录！添加失败");
            return resultMap;
        }
        // 检查库存数量是否足够
        Integer amount = goodsMapper.getAmountOfProduct(cart.getProductID());
        if(amount<cart.getAmount()){
            resultMap.put("code", 400);
            resultMap.put("message","库存不足！请减少添加数量或联系销售员！");
            return resultMap;
        }
        // 设置其他信息
        cart.setUserID(userID);
        LocalDateTime nowTime = LocalDateTime.now();
        cart.setAddTime(nowTime);
        int result = cartMapper.addRecordToCart(cart);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","添加成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","添加失败！");
        }
        return resultMap;
    }

    /**
     * 获取购物车记录及详情
     * @param session session
     * @return 购物车详情
     */
    public List<detailCart> showRecordFromCart(HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        List<detailCart> detailCartList = new ArrayList<>();
        // 查询用户的购物车记录
        List<Cart> carts = cartMapper.searchRecord(userID);
        for(Cart cartItem : carts){
            detailCart ditem = new detailCart();
            Integer id = cartItem.getId();
            // 根据productID获取商品信息
            Product product = goodsMapper.showProductByID(cartItem.getProductID());
            // 设置信息
            ditem.setProductId(cartItem.getProductID());
            ditem.setKindId(product.getKindId());
            ditem.setId(id);
            ditem.setSalesmanName(product.getSalesmanId());
            ditem.setTitle(product.getTitle());
            ditem.setImg(product.getImg());
            ditem.setAmount(cartItem.getAmount());
            ditem.setFinalPrice(product.getFinalPrice());
            // 追加记录
            detailCartList.add(ditem);
        }
        return detailCartList;
    }

    /**
     * 删除购物车记录
     * @param cart 购物车记录
     * @return 删除结果
     */
    @MyLog(role = "用户", oper = "购物车操作", operType = "删除", mess = "删除购物车商品")
    public Map<String, Object> deleteRecordByID(Cart cart) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = cartMapper.deleteRecordByID(cart);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","删除成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","删除失败！");
        }
        return resultMap;
    }

    /**
     * 清空购物车
     * @param session session信息
     * @return 清空结果
     */
    @MyLog(role = "用户", oper = "购物车操作", operType = "清空", mess = "清空购物车")
    public Map<String, Object> clearCart(HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        Map<String, Object> resultMap = new HashMap<>();
        int result = cartMapper.clearCart(userID);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","清空成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","清空失败！");
        }
        return resultMap;
    }
}
