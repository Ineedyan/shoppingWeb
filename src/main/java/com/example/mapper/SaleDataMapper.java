package com.example.mapper;

import com.example.pojo.SaleData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SaleDataMapper {

    /**
     * 插入一条商品的销售数据
     * @param saleData 销售数据
     * @return 插入结果
     */
    @Insert("INSERT INTO saledata(salesmanName, productId, kindId, num, totalSale) " +
            "VALUES (#{salesmanName}, #{productId}, #{kindId},#{num}, #{totalSale})")
    int addProductSaleData(SaleData saleData);

    /**
     * 获取销售员所属商品的销售数据
     * @param salesmanName 销售人员名
     * @return 销售数据列表
     */
    @Select("SELECT * FROM saledata WHERE salesmanName = #{salesmanName}")
    List<SaleData> getDataBySalesman(String salesmanName);

    /**
     * 根据商品id查询销售数据
     * @param productId 商品id
     * @return 销售记录
     */
    @Select("SELECT productId, num, totalSale FROM saledata WHERE productId = #{productId}")
    SaleData getDataByProductId(Integer productId);

    /**
     * 更新销售数据
     * @param saleData 销售数据
     */
    @Update("UPDATE saledata SET num = #{num}, totalSale = #{totalSale} WHERE productId = #{productId}")
    void updateData(SaleData saleData);

    @Update("UPDATE saledata SET kindId = #{kindId} WHERE productId = #{productId}")
    int updateKind(Integer productId, Integer kindId);

    /**
     * 获取所有销售数据
     * @return 销售数据列表
     */
    @Select("SELECT * FROM saledata")
    List<SaleData> getAllData();

    /**
     * 根据类别筛选销售数据
     * @param KindId 类别ID
     * @return 销售数据
     */
    @Select("SELECT * from saledata WHERE kindId = #{kindId}")
    List<SaleData> getDataByKind(Integer KindId);
}
