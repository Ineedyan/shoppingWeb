package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String title;
    private Integer amount;
    private Double totalPrice;
}
