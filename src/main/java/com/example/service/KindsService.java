package com.example.service;

import com.example.mapper.KindsMapper;
import com.example.pojo.ProductKind;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KindsService {

    @Resource
    private KindsMapper kindsMapper;

    /**
     * 获取商品类别信息列表
     * @return 所有商品类别信息
     */
    public List<ProductKind> getAllProductKinds() {
        return kindsMapper.getAllProductKinds();
    }

    /**
     * 删除商品类别
     * @param productKind 商品类别对象
     * @return 删除结果
     */
    @MyLog(role = "销售人员", oper = "商品类别操作", operType = "删除", mess = "删除商品类别")
    public Map<String, Object> deleteKindsInfo(ProductKind productKind) {
        Map<String, Object> resultMap = new HashMap<>();
        String kind = kindsMapper.getKindById(productKind);
        int result = kindsMapper.deleteKindsInfo(productKind);
        if(result > 0){
            resultMap.put("code", 200);
            String mes = "删除类别：" + kind + "成功！";
            resultMap.put("message", mes);
        }
        else {
            resultMap.put("code", 400);
            String mes = "删除类别：" + kind + "失败！";
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /**
     * 添加类别信息
     * @param productKind 类别信息
     * @return 添加结果
     */
    @MyLog(role = "销售人员", oper = "商品类别操作", operType = "新增", mess = "新增商品类别")
    public Map<String, Object> addKindsInfo(String productKind) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = kindsMapper.addKindsInfo(productKind);
        if(result > 0){
            resultMap.put("code", 200);
            String mes = "添加类别：" + productKind + "成功！";
            resultMap.put("message", mes);
        }
        else {
            resultMap.put("code", 400);
            String mes = "添加类别：" + productKind + "失败！";
            resultMap.put("message", mes);
        }
        return resultMap;
    }
}
