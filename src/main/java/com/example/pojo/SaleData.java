package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleData {
    private Integer id;
    private Integer productId;
    private Integer kindId;
    private String kind;
    private String salesmanName;
    private String title;
    private String img;
    private Integer num;
    private Double totalSale;

}
