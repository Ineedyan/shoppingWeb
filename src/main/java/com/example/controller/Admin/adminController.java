package com.example.controller.Admin;

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
public class adminController {

    @Resource
    private AdminService adminService;

    /**
     * 登录管理员账户接口
     * time: 2024/05/10 03:06
     * @param user 账号信息
     * @param session session信息
     * @return 登陆结果
     */
    @PostMapping  ("adminLogin")
    public Map<String, Object> adminLoginAccount(User user, HttpSession session){
        return adminService.adminLoginAccount(user, session);
    }

    /**
     * 获取已登录的账号信息接口
     * time: 2024/05/10 03:07
     * @param session session信息
     * @return 账号信息
     */
    @PostMapping("getInfo")
    public User getInfo(HttpSession session){
        return adminService.getInfo(session);
    }

    /**
     * 账号登出 接口
     * @param session session信息
     * @return 退出结果
     */
    @PostMapping("logout")
    public Map<String, String> logout(HttpSession session) {
        return adminService.logout(session);
    }

    /* *********************
     **以下是用户管理模块接口***
     * *********************/

    /**
     * 获取所有用户信息
     * time: 2024/05/10 03:15
     * @return 用户信息列表
     */
    @PostMapping("Users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    /**
     * 根据ID获取用户名 接口
     * @param id 用户ID
     * @return 用户名(email)
     */
    @PostMapping("getUsernameById")
    public User getUsernameById(Integer id){
        return adminService.getUsernameById(id);
    }

    /**
     * 添加普通用户账户 接口
     * time: 2024/05/10 14:09
     * @param user 用户账户信息
     * @return 添加结果
     */
    @PostMapping("addUser")
    public Map<String,Object> addAccount(User user){
        return adminService.addAccount(user);
    }

    /**
     * 删除普通用户账号 接口
     * time: 2024/05/10 14:17
     * @param user 用户账户信息
     * @return 删除结果
     */
    @PostMapping("deleteUser")
    public Map<String,Object> delAccount(User user){
        return adminService.delAccount(user);
    }

    /**
     * 更改普通用户的密码 接口
     * @param user 用户信息
     * @return 更改结果
     */
    @PostMapping("updateUserPassword")
    public Map<String,Object> updatePwOfUser(User user){
        return adminService.updatePwOfUser(user);
    }

    /**
     * 更改普通用户的激活状态 接口
     * @param user 用户信息（ID、isValid）
     * @return 更改结果
     */
    @PostMapping("updateUserState")
    public Map<String,Object> updateStateOfUser(User user){
        return adminService.updateStateOfUser(user);
    }

    /* *********************
     **以下是销售人员管理模块接口***
     * *********************/

    /**
     * 获取所有销售人员信息
     * time: 2024/05/10 03:15
     * @return 销售人员信息列表
     */
    @PostMapping("Salesmen")
    public List<User> getAllSalesmen(){
        return adminService.getAllSalesmen();
    }

    /**
     * 添加销售人员账户 接口
     * time: 2024/05/10 20：25
     * @param user 销售人员账户信息
     * @return 添加结果
     */
    @PostMapping("addSalesman")
    public Map<String,Object> addSalesman(User user){
        return adminService.addSalesman(user);
    }

    /**
     * 删除销售人员账户 接口
     * time: 2024/05/10 20:32
     * @param user 销售人员账户信息
     * @return 删除结果
     */
    @PostMapping("deleteSalesman")
    public Map<String, Object> deleteSalesman(User user){
        return adminService.deleteSalesman(user);
    }

    /**
     * 更改销售人员的密码 接口
     * @param user 销售人员信息
     * @return 更改结果
     */
    @PostMapping("updateSalesmanPassword")
    public Map<String,Object> updatePwOfSalesman(User user){
        return adminService.updatePwOfSalesman(user);
    }

    /**
     * 更改销售人员的激活状态 接口
     * @param user 销售人员信息（ID、isValid）
     * @return 更改结果
     */
    @PostMapping("updateSalesmanState")
    public Map<String,Object> updateStateOfSalesman(User user){
        return adminService.updateStateOfSalesman(user);
    }
}
