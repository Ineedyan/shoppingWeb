package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeData {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer orderNum;
    private double totalPrice;
    private double avgPrice;
    private String kindMost;
}
