package com.example.controller;

import com.example.pojo.ProductKind;
import com.example.service.KindsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("productKinds")
public class kindsController {
    @Resource
    private KindsService kindsService;

    /**
     * 获取商品类别信息接口
     * @return 商品类别列表
     */
    @PostMapping("kinds")
    public List<ProductKind> getAllProductKinds(){
        return kindsService.getAllProductKinds();
    }

    /**
     * 添加类别信息接口
     * @param productKind 类别信息
     * @return 添加结果
     */
    @PostMapping("add")
    public Map<String, Object> addKindsInfo(String productKind){
        return kindsService.addKindsInfo(productKind);
    }

    /**
     * 删除商品类别信息 接口
     * @param productKind 类别对象
     * @return 删除结果
     */
    @PostMapping("Delete")
    public Map<String, Object> deleteKindsInfo(ProductKind productKind){
        return kindsService.deleteKindsInfo(productKind);
    }

}
