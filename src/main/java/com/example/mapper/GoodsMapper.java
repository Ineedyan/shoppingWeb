package com.example.mapper;

import com.example.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper {
    /*
     * 获取所以商品信息
     * */
    @Select("SELECT * FROM goods")
    List<Product> getAllProducts();

    /*
    * 显示商品信息
    * */
    @Select("SELECT * from goods WHERE id = #{ID}")
    @Results({
            @Result(property = "title", column = "title"),
            @Result(property = "kind", column = "kind"),
            @Result(property = "img", column = "img"),
            @Result(property = "originalPrice", column = "originalPrice"),
            @Result(property = "discount", column = "discount"),
            @Result(property = "finalPrice", column = "finalPrice"),
            @Result(property = "num", column = "num")
    })
    Product showProductByID(String ID);

    /*
    * 更改商品信息
    * */
    @Update("UPDATE goods SET title=#{title}, kind=#{kind}, originalPrice=#{originalPrice}, discount=#{discount}, originalPrice=#{originalPrice}, num=#{num} WHERE id=#{id} ")
    int updateGoodsInfo(Product product);

    /*
     * 删除商品信息
     * */
    @Delete("DELETE FROM goods WHERE id=#{id}")
    int deleteGoodsInfo(Product product);

    /*
     * 添加商品信息
     * */
    @Insert("INSERT INTO goods(title, kind, img, originalPrice, discount, finalPrice, num)" +
            "VALUES ( #{title}, #{kind}, #{img}, #{originalPrice}, #{discount}, #{finalPrice}, #{num})")
    int addGoodsInfo(Product product);

    @Select("SELECT * FROM goods WHERE title LIKE CONCAT('%',#{searchWord},'%')")
    List<Product> searchGoods(String searchWord);
}
