package com.example.mapper;

import com.example.pojo.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CartMapper {
    /**
     * 添加购物车记录
     * @param cart 购物车记录
     * @return 添加结果
     */
    @Insert("INSERT INTO cart(userID, productID, amount, addTime)" +
            "VALUES ( #{userID}, #{productID}, #{amount}, #{addTime})")
    int addRecordToCart(Cart cart);

    /**
     * 根据userid获取购物车记录
     * @param userID userid
     * @return 购物车记录
     */
    @Select("SELECT * FROM cart WHERE userID=#{userID}")
    List<Cart> searchRecord(Integer userID);

    /**
     * 根据userid删除购物车记录
     * @param cart 购物车记录id
     * @return 删除结果
     */
    @Delete("DELETE FROM cart WHERE id=#{id}")
    int deleteRecordByID(Cart cart);

    /**
     * 根据userid清空购物车
     * @param userID userid
     * @return 清空结果
     */
    @Delete("DELETE FROM cart WHERE userID=#{userID}")
    int clearCart(Integer userID);
}
