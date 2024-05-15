package com.example.mapper;

import com.example.pojo.ProductKind;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KindsMapper {
    /**
     * 查询所有类别信息
     * @return 查询结果
     */
    @Select("SELECT * FROM productkind")
    List<ProductKind> getAllProductKinds();

    /**
     * 删除类别信息
     * @param productKind 类别id
     * @return 删除结果
     */
    @Delete("DELETE FROM productkind WHERE id = #{id}")
    int deleteKindsInfo(ProductKind productKind);

    /**
     * 插入商品类别
     * @param productKind 类别信息
     * @return 插入结果
     */
    @Insert("INSERT INTO productkind(productKind) VALUES (#{productKind})")
    int addKindsInfo(String productKind);

    /**
     * 根据类别名获取类别id
     * @param kind 类别名
     * @return 类别id
     */
    @Select("SELECT id from productkind WHERE productKind = #{kind}")
    Integer getKindIdByName(String kind);

    /**
     * 根据id获取类别名
     * @param productKind 类别id
     * @return 类别名
     */
    @Select("SELECT productKind from productkind WHERE id = #{id}")
    String getKindById(ProductKind productKind);
}
