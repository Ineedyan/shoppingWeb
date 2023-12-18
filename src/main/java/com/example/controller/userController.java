package com.example.controller;


import com.example.pojo.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class userController {
    @Resource
    private UserService userService;

    // 注册账号
    @PostMapping("create")
    public Map<String,Object> createAccount(User user){
        return userService.createAccount(user);
    }

    /*
     * 登录账号
     * */
    @PostMapping  ("login")
    public Map<String, Object> loginAccount(User user, HttpSession session){
        return userService.loginAccount(user, session);
    }

    // 账号激活
    @GetMapping("activation")
    public Map<String, Object> activationAccount(String confirmCode){
        return userService.activationAccount(confirmCode);
    }

    @PostMapping("getInfo")
    public User getInfo(HttpSession session){
        return userService.getInfo(session);
    }

    @PostMapping("logout")
    public Map<String, String> logout(HttpSession session) {
        session.removeAttribute("userID");
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Logout successful");
        return response;
    }

}
