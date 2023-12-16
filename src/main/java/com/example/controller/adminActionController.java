package com.example.controller;

import com.example.pojo.User;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class adminActionController {

    @Resource
    private AdminService adminService;

    /*
     * 登录管理员账号
     * */
    @PostMapping  ("adminLogin")
    public Map<String, Object> adminLoginAccount(User user, HttpSession session){
        return adminService.adminLoginAccount(user, session);
    }

    /*
     * 获取所有用户
     * */
    @PostMapping("Users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    /*
     * 添加账号
     * */
    @PostMapping("add")
    public Map<String,Object> addAccount(User user){
        return adminService.addAccount(user);
    }

    /*
     * 删除账号
     * */
    @PostMapping("delete")
    public Map<String,Object> delAccount(User user){
        return adminService.delAccount(user);
    }

    /*
     * 修改账号
     * */
    @PostMapping("update")
    public Map<String,Object> updateAccount(User user){
        return adminService.updateAccount(user);
    }
}
