package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBuy {
    private Integer id;
    private Integer userId;
    private Integer kindId;
    private Integer count;
}
