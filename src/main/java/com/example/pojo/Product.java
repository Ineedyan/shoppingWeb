package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private Integer  id;  // 主键
    private String salesmanId; // 所属销售人员
    private String title; // 商品标题
    private Integer kindId; // 分类ID
    private String kind; // 分类
    private String img;  // 图片路径
    private Double originalPrice;  // 原始价格
    private Double discount;  // 折扣
    private Double finalPrice;  // 现价
    private Integer num;  // 库存量
}
