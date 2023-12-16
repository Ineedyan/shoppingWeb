package com.example.mapper;

import com.example.pojo.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CartMapper {
    // 添加购物车记录
    @Insert("INSERT INTO cart(userID, productID, amount, addTime)" +
            "VALUES ( #{userID}, #{productID}, #{amount}, #{addTime})")
    int addRecordToCart(Cart cart);

    // 根据userID搜索其购物记录
    @Select("SELECT * FROM cart WHERE userID=#{userID}")
    List<Cart> searchRecord(Integer userID);

    // 根据记录编号删除购物车记录
    @Delete("DELETE FROM cart WHERE id=#{id}")
    int deleteRecordByID(Cart cart);

    @Delete("DELETE FROM cart WHERE userID=#{userID}")
    int clearCart(Integer userID);
}
