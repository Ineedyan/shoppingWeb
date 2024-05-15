package com.example.controller;

import com.example.pojo.Product;
import com.example.service.GoodsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class goodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 获取所有商品信息
     * @return 商品列表
     */
    @PostMapping("Goods")
    public List<Product> getAllProduct(){
        return goodsService.getAllProducts();
    };

    /**
     * 根据销售人员显示商品
     * @param session session信息
     * @return 商品列表
     */
    @PostMapping("GoodsBySalesman")
    public List<Product> getProductsBySalesman(HttpSession session){
        return goodsService.getProductsBySalesman(session);
    }

    /**
     * 根据ID显示商品信息
     * @param ID 商品ID
     * @return 商品信息
     */
    @PostMapping("Show")
    public Product editGoodsInfo(Integer ID){
        return goodsService.showGoodsInfo(ID);
    }

    /**
     * 销售人员更新商品信息 接口
     * @param product 商品信息
     * @return 更新结果
     */
    @PostMapping("Update")
    public Map<String,Object> updateGoodsInfo(Product product){
        return goodsService.updateGoodsInfo(product);
    }

    /**
     * 删除商品信息 接口
     * @param product 商品信息
     * @return 删除结果
     */
    @PostMapping("Delete")
    public Map<String,Object> deleteGoodsInfo(Product product){
        return goodsService.deleteGoodsInfo(product);
    }

    /**
     * 添加商品信息 接口
     * @param product 商品信息
     * @param session 销售人员session
     * @return 添加结果
     */
    @PostMapping("Add")
    public Map<String,Object> addGoodsInfo(Product product, HttpSession session){
        return goodsService.addGoodsInfo(product, session);
    }

    /**
     * 管理员删除商品信息 接口
     * @param product 商品信息
     * @return 删除结果
     */
    @PostMapping("deleteByAdmin")
    public Map<String,Object> deleteByAdmin(Product product){
        return goodsService.deleteByAdmin(product);
    }

    /**
     * 管理员更改商品信息 接口
     * @param product 商品信息
     * @return 更改结果
     */
    @PostMapping("updateByAdmin")
    public Map<String,Object> updateByAdmin(Product product){
        return goodsService.updateByAdmin(product);
    }

    /**
     * 管理员根据对象获取商品 接口
     * @param Object 获取对象
     * @return 商品列表
     */
    @PostMapping("getGoodsBySalesman")
    public List<Product> getGoodsBySalesman(@RequestParam(value = "object") String Object){
        return goodsService.getGoodsBySalesman(Object);
    }

    /**
     * 管理员根据商品类别获取商品 接口
     * @param kind 商品类别
     * @return 商品列表
     */
    @PostMapping("getGoodsByKind")
    public List<Product> getGoodsByKind(@RequestParam(value = "kind")String kind){
        return goodsService.getGoodsByKind(kind);
    }

    /**
     * 根据关键词搜索产品
     * @param content 关键词
     * @return 产品
     */
    @GetMapping("search")
    public List<Product> searchGoods(@RequestParam String content){
        return goodsService.searchGoods(content);
    }

    /**
     * 获取产品推荐
     * @param session 用户session
     * @return 产品推荐
     */
    @PostMapping("getRecommend")
    public List<Product> getRecommend(HttpSession session){
        return goodsService.getRecommend(session);
    }
}
