package com.example.mapper;

import com.example.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper {

    /**
     * 获取全部商品
     * @return 全部商品信息
     */
    @Select("SELECT * FROM goods")
    List<Product> getAllProducts();

    /**
     * 根据销售人员ID获取商品
     * @param salesmanId 销售人员ID
     * @return 商品
     */
    @Select("SELECT * FROM goods WHERE salesmanId = #{salesmanId}")
    List<Product> getProductsBySalesman(String salesmanId);

    /**
     * 商品信息
     * @param ID 商品id
     * @return 商品信息
     */
    @Select("SELECT * from goods WHERE id = #{ID}")
    @Results({
            @Result(property = "salesmanId", column = "salesmanId"),
            @Result(property = "title", column = "title"),
            @Result(property = "kindId", column = "kindId"),
            @Result(property = "kind", column = "kind"),
            @Result(property = "img", column = "img"),
            @Result(property = "originalPrice", column = "originalPrice"),
            @Result(property = "discount", column = "discount"),
            @Result(property = "finalPrice", column = "finalPrice"),
            @Result(property = "num", column = "num")
    })
    Product showProductByID(Integer ID);

    /**
     * 更改商品信息
     * @param product 商品信息
     * @return 更改结果
     */
    @Update("UPDATE goods SET title=#{title},kindId=#{kindId}, kind=#{kind}, originalPrice=#{originalPrice}, discount=#{discount}, finalPrice=#{finalPrice}, num=#{num} WHERE id=#{id} ")
    int updateGoodsInfo(Product product);

    /**
     * 删除商品信息
     * @param product 商品
     * @return 删除结果
     */
    @Delete("DELETE FROM goods WHERE id=#{id}")
    int deleteGoodsInfo(Product product);

    /**
     * 添加商品信息
     * @param product 商品信息
     * @return 添加结果
     */
    @Insert("INSERT INTO goods(title, salesmanId, kindId, kind, img, originalPrice, discount, finalPrice, num)" +
            "VALUES ( #{title},#{salesmanId},#{kindId}, #{kind}, #{img}, #{originalPrice}, #{discount}, #{finalPrice}, #{num})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addGoodsInfo(Product product);

    /**
     * 商品搜索
     * @param searchWord 搜索词
     * @return 搜索结果
     */
    @Select("SELECT * FROM goods WHERE title LIKE CONCAT('%',#{searchWord},'%')")
    List<Product> searchGoods(String searchWord);

    /**
     * 获取商品库存
     * @param productID 商品ID
     * @return 商品库存
     */
    @Select("SELECT num FROM goods WHERE id = #{productID}")
    Integer getAmountOfProduct(Integer productID);

    /**
     * 更新商品库存
     * @param productId 商品ID
     * @param newNum 新库存数量
     */
    @Update("UPDATE goods SET num = #{newNum} WHERE id = #{productId}")
    void updateNumOfProduct(Integer productId, int newNum);

    /**
     * 根据商品类别获取商品
     * @param kind 商品类别
     * @return 商品信息
     */
    @Select("SELECT * FROM goods WHERE kind = #{kind}")
    List<Product> getProductsByKind(String kind);

    /**
     * 根据商品类别随机获取一个商品
     * @param kindId 商品类别ID
     * @return 商品
     */
    @Select("SELECT * FROM goods " +
            "WHERE kindId = #{kindId} " +
            "ORDER BY RAND()" +
            "LIMIT 1")
    Product getRecommendProduct(Integer kindId);

    /**
     * 随机获取两个商品
     * @return 商品
     */
    @Select("SELECT * FROM goods " +
            "ORDER BY RAND()" +
            "LIMIT 2")
    List<Product> getRandomRecommend();
}
