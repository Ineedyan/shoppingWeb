package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperExcpLog {
    private Integer id;
    private String methodName;
    private String role;
    private String username;
    private String operationTime;
    private String ip;
}
