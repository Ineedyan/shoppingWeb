package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private Integer id;
    private Integer userID;
    private Integer productID;
    private Integer amount;
    private LocalDateTime addTime;
}
