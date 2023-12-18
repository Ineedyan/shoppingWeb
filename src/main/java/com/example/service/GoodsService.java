package com.example.service;

import com.example.mapper.GoodsMapper;
import com.example.pojo.Product;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    /*
     * 返回所有商品信息
     * */
    public List<Product> getAllProducts() {
        return goodsMapper.getAllProducts();
    }

    /*
    * 显示商品信息
    * */
    public Product showGoodsInfo(String ID) {
        return goodsMapper.showProductByID(ID);
    }

    /*
    * 更新商品信息
    * */
    public Map<String, Object> updateGoodsInfo(Product product) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = goodsMapper.updateGoodsInfo(product);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","修改成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","修改失败！");
        }
        return resultMap;
    }

    /*
    * 删除商品信息
    * */
    public Map<String, Object> deleteGoodsInfo(Product product) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = goodsMapper.deleteGoodsInfo(product);
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

    /*
     * 添加商品信息
     * */
    public Map<String, Object> addGoodsInfo(Product product){
        Map<String, Object> resultMap = new HashMap<>();
        int result = goodsMapper.addGoodsInfo(product);
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

    // 根据关键词搜索商品信息
    public List<Product> searchGoods(String searchWord) {
        return goodsMapper.searchGoods(searchWord);
    }
}
