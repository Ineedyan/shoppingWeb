package com.example.service;

import com.example.mapper.GoodsMapper;
import com.example.mapper.KindsMapper;
import com.example.mapper.SaleDataMapper;
import com.example.pojo.Product;
import com.example.pojo.ProductKind;
import com.example.pojo.SaleData;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleDataService {

    @Resource
    private SaleDataMapper saleDataMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private KindsMapper kindsMapper;
    /**
     * 获取销售人员所属商品的销售数据
     * @param session 销售人员session
     * @return 销售数据
     */
    public List<SaleData> getDataBySalesman(HttpSession session) {
        List<SaleData> saleData = saleDataMapper.getDataBySalesman((String) session.getAttribute("salesmanName"));
        for(SaleData data:saleData){
            Product product = goodsMapper.showProductByID(data.getProductId());
            data.setImg(product.getImg());
            data.setTitle(product.getTitle());
            data.setKind(kindsMapper.getKindById(new ProductKind(data.getKindId())));
        }
        return saleData;
    }

    /**
     * 获取所有销售数据
     * @return 所有销售数据
     */
    public List<SaleData> getAllData() {
        List<SaleData> saleData = saleDataMapper.getAllData();
        for(SaleData data:saleData){
            Product product = goodsMapper.showProductByID(data.getProductId());
            data.setImg(product.getImg());
            data.setTitle(product.getTitle());
            data.setKind(kindsMapper.getKindById(new ProductKind(data.getKindId())));
        }
        return saleData;
    }

    /**
     * 管理员查询销售人员所属商品的销售数据
     * @param object 查询对象
     * @return 销售数据
     */
    public List<SaleData> adminGetDataBySalesman(String object) {
        List<SaleData> saleData = saleDataMapper.getDataBySalesman(object);
        for(SaleData data:saleData){
            Product product = goodsMapper.showProductByID(data.getProductId());
            data.setImg(product.getImg());
            data.setTitle(product.getTitle());
            data.setKind(kindsMapper.getKindById(new ProductKind(data.getKindId())));
        }
        return saleData;
    }

    /**
     * 根据类别筛选销售数据
     * @param kind 类别
     * @return 销售数据
     */
    public List<SaleData> getDataByKind(String kind) {
        Integer id = kindsMapper.getKindIdByName(kind);
        List<SaleData> saleData = saleDataMapper.getDataByKind(id);
        for(SaleData data:saleData){
            Product product = goodsMapper.showProductByID(data.getProductId());
            data.setImg(product.getImg());
            data.setTitle(product.getTitle());
            data.setKind(kind);
        }
        return saleData;
    }
}
