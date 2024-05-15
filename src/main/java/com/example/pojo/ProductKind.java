package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductKind {
    private Integer id;
    private String productKind;

    public ProductKind(Integer integer) {
        this.id = integer;
    }
}
