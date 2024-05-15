package com.example.service;

import cn.hutool.crypto.SecureUtil;
import com.example.mapper.SalesmanMapper;
import com.example.pojo.User;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesmanService {
    @Resource
    private SalesmanMapper salesmanMapper;

    /**
     * 验证登录
     * @param user 账户信息
     * @param session session
     * @return 登录结果
     */
    @MyLog(role = "销售人员", oper = "账户操作", operType = "登录", mess = "销售人员账户登录")
    public Map<String, Object> salesmanLoginAccount(User user, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        // 根据邮箱查询用户
        List<User> userList = salesmanMapper.selectUserByEmail(user.getEmail());
        // 查询不到用户，返回不存在
        if (userList == null || userList.isEmpty()) {
            resultMap.put("code", 400);
            resultMap.put("message","该账户不存在！请检查输入！");
            return resultMap;
        }
        // 查询到多个，返回账号异常，联系管理员
        if (userList.size()>1) {
            resultMap.put("code", 400);
            resultMap.put("message","多账户异常！请联系管理员！");
            return resultMap;
        }
        // 查询到一个用户，进行状态校验和密码比对
        User u = userList.get(0);
        if(u.getIsValid()==0){
            resultMap.put("code", 400);
            resultMap.put("message","该账户未激活！请先前往邮箱进行账户激活！");
            return resultMap;
        }
        if (u.getIsValid()==2) {
            resultMap.put("code", 400);
            resultMap.put("message","该账户已经被拉黑！请联系网站管理员！");
            return resultMap;
        }
        if(u.getIsValid()!=1){
            resultMap.put("code", 400);
            resultMap.put("message","账户状态异常！请联系管理员");
            return resultMap;
        }
        String userPswInput = SecureUtil.md5(user.getPassword() + u.getSalt());
        if(!u.getPassword().equals(userPswInput)){
            resultMap.put("code", 400);
            resultMap.put("message","用户名或密码错误！");
            return resultMap;
        }
        resultMap.put("code", 200);
        resultMap.put("message","登录成功！");
        session.setAttribute("salesmanID", u.getId());
        session.setAttribute("salesmanName", u.getEmail());
        session.setMaxInactiveInterval(2*60);
        return resultMap;
    }

    /**
     * 获取已登录的账号信息
     * @param session session
     * @return 账号信息
     */
    public User getInfo(HttpSession session) {
        Integer id = (Integer) session.getAttribute("salesmanID");
        return salesmanMapper.getUserInfo(id);
    }

    /**
     * 账号退出登录
     * @param session session信息
     * @return 退出登录结果
     */
    @MyLog(role = "销售人员", oper = "账户操作", operType = "登出", mess = "销售人员账户登出")
    public Map<String, String> logout(HttpSession session) {
        session.removeAttribute("salesmanID");
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "登出成功！");
        return response;
    }


}
