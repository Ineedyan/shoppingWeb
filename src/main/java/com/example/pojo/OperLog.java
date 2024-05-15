package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperLog {
    private Integer id;
    private String role;
    private String username;
    private String operation;
    private String operationType;
    private String operationTime;
    private String message;
    private String ip;
    private String result;

}
