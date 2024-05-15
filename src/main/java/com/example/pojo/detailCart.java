package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class detailCart implements Serializable {
    private Integer id;
    private Integer productId;
    private Integer kindId;
    private String salesmanName;
    private String title;
    private String img;
    private Double finalPrice;
    private Integer amount;
}
