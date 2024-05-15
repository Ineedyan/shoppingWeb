package com.example.controller.Salesman;

import com.example.pojo.User;
import com.example.service.SalesmanService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/salesman")
public class salesmanController {
    @Resource
    private SalesmanService salesmanService;

    /**
     * 登录销售人员账号接口
     * @param user 账号信息
     * @param session 验证登录
     * @return 登录结果
     */
    @RequestMapping("salesmanLogin")
    public Map<String, Object> salesmanLoginAccount(User user, HttpSession session){
        return salesmanService.salesmanLoginAccount(user, session);
    }

    /**
     * 获取已登录的账号信息接口
     * @param session session
     * @return 账号信息
     */
    @PostMapping("getInfo")
    public User getInfo(HttpSession session){
        return salesmanService.getInfo(session);
    }

    /**
     * 账号退出登录接口
     * @param session session信息
     * @return 退出登录结果
     */
    @PostMapping("logout")
    public Map<String, String> logout(HttpSession session) {
        return salesmanService.logout(session);
    }

}
