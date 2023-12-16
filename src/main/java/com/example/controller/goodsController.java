package com.example.controller;

import com.example.pojo.Product;
import com.example.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class goodsController {

    @Resource
    private GoodsService goodsService;

    /*
     * 获取所有商品信息
     * */
    @PostMapping("Goods")
    public List<Product> getAllProducts(){
        return goodsService.getAllProducts();
    }

    /*
     * 根据ID显示商品信息
     * */
    @PostMapping("Show")
    public Product editGoodsInfo(String ID){
        return goodsService.showGoodsInfo(ID);
    }

    /*
     * 更新商品信息
     * */
    @PostMapping("Update")
    public Map<String,Object> updateGoodsInfo(Product product){
        return goodsService.updateGoodsInfo(product);
    }

    /*
     * 删除商品信息
     * */
    @PostMapping("Delete")
    public Map<String,Object> deleteGoodsInfo(Product product){
        return goodsService.deleteGoodsInfo(product);
    }

    /*
     * 添加商品信息
     * */
    @PostMapping("Add")
    public Map<String,Object> addGoodsInfo(Product product){
        return goodsService.addGoodsInfo(product);
    }

    // 根据关键词搜索产品
    @GetMapping("search")
    public List<Product> searchGoods(@RequestParam String content){
        return goodsService.searchGoods(content);
    }
}
