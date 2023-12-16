package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable{
    private Integer  id;  // 主键
    private Integer userId; // 用户id
    private String province; // 省份
    private String city;  // 城市
    private String district;  // 区县
    private String detailArea;  // 详细地址
    private String name;  // 收货名
    private String phoneNum; // 收货电话
}
