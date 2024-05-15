package com.example.controller;

import com.example.pojo.SaleData;
import com.example.service.SaleDataService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/SaleData")
public class saleDataController {
    @Resource
    private SaleDataService saleDataService;

    /**
     * 获取销售人员所属商品的销售数据
     * @param session 销售人员session
     * @return 销售数据
     */
    @PostMapping("getDataBySalesman")
    List<SaleData> getDataBySalesman(HttpSession session){
        return saleDataService.getDataBySalesman(session);
    }

    /**
     * 获取所有销售数据
     * @return 所有销售数据
     */
    @PostMapping("getAllData")
    List<SaleData> getAllData(){
        return saleDataService.getAllData();
    }


    /**
     * 管理员查询销售人员所属商品的销售数据
     * @param object 查询对象
     * @return 销售数据
     */
    @PostMapping("adminGetDataBySalesman")
    List<SaleData> getDataBySalesman(@RequestParam(value = "object") String object){
        return saleDataService.adminGetDataBySalesman(object);
    }

    /**
     * 根据类别筛选销售数据
     * @param kind 类别
     * @return 销售数据
     */
    @PostMapping("getDataByKind")
    List<SaleData> getDataByKind(@RequestParam(value = "kind")String kind){
        return saleDataService.getDataByKind(kind);
    }
}
