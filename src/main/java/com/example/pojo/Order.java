package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Integer id;
    private Integer userId;
    private String salesmanName;
    private LocalDateTime orderTime;
    private Double totalPrice;
    private String address;
    private String state;
    private String expressNumber;

}
