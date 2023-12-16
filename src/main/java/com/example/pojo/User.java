package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
    private Integer  id;  // 主键
    private String email; // 邮箱
    private String password; //密码，使用md5+盐加密
    private String salt;  // 盐
    private String confirmCode;  //确认码
    private LocalDateTime activationTime;  //有效期
    private Byte isValid;  //是否可用
}
