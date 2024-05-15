package com.example.controller.User;


import com.example.pojo.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class userController {
    @Resource
    private UserService userService;

    /**
     * 账号注册
     * @param user 账号信息
     * @return 注册结果
     */
    @PostMapping("create")
    public Map<String,Object> createAccount(User user){
        return userService.createAccount(user);
    }

    /**
     * 账户登录
     * @param user 账号信息
     * @param session 用户session
     * @return 登录结果
     */
    @PostMapping  ("login")
    public Map<String, Object> loginAccount(User user, HttpSession session){
        return userService.loginAccount(user, session);
    }

    /**
     * 账号激活
     * @param confirmCode 确认码
     * @return 激活结果
     */
    @GetMapping("activation")
    public Map<String, Object> activationAccount(String confirmCode){
        return userService.activationAccount(confirmCode);
    }

    /**
     * 获取用户基本信息
     * @param session session
     * @return 基本信息（userID、email）
     */
    @PostMapping("getInfo")
    public User getInfo(HttpSession session){
        return userService.getInfo(session);
    }

    /**
     * 退出登录
     * @param session 用户session
     * @return 退出结果
     */
    @PostMapping("logout")
    public Map<String, String> logout(HttpSession session) {
        return userService.logout(session);
    }

}
