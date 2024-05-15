package com.example.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.mapper.AdminMapper;
import com.example.pojo.User;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Resource
    private AdminMapper adminMapper;

    /**
     * 登录管理人员账号
     * @param user 账户
     * @param session session
     * @return 登录结果
     */
    @MyLog(role = "管理员", oper = "账户操作", operType = "登录", mess = "管理员账号登录")
    public Map<String, Object> adminLoginAccount(User user, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        // 根据邮箱查询用户
        List<User> userList = adminMapper.selectUserByEmail(user.getEmail());
        // 查询不到用户，返回不存在或未激活
        if (userList == null || userList.isEmpty()) {
            resultMap.put("code", 400);
            resultMap.put("message","该账户不存在或未激活");
            return resultMap;
        }
        // 查询到多个，返回账号异常，联系管理员
        if (userList.size()>1) {
            resultMap.put("code", 400);
            resultMap.put("message","账户异常！请联系管理员！");
            return resultMap;
        }
        // 查询到一个用户，进行密码比对
        User u = userList.get(0);
        String userPswInput = SecureUtil.md5(user.getPassword() + u.getSalt());
        if(!u.getPassword().equals(userPswInput)){
            resultMap.put("code", 400);
            resultMap.put("message","用户名或密码错误！");
            return resultMap;
        }
        resultMap.put("code", 200);
        resultMap.put("message","登录成功！");
        session.setAttribute("adminId", u.getId());
        session.setAttribute("adminName", u.getEmail());
        session.setMaxInactiveInterval(2*60);
        return resultMap;
    }

    /**
     * 返回所有用户
     * @return 所有用户信息
     */
    public List<User> getAllUsers(){
        List<User> res = adminMapper.getAllUser();
        // 计算出用户的注册时间
        for (User re : res) {
            LocalDateTime registerTime = re.getActivationTime().minusDays(1);
            re.setActivationTime(registerTime);
        }
        return res;
    }

    /**
     * 根据ID获取用户名
     * @param id 用户ID
     * @return 获取结果
     */
    public User getUsernameById(Integer id) {
        return adminMapper.getUsernameById(id);
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return 添加结果
     */
    @MyLog(role = "管理员", oper = "对用户账户操作", operType = "新增", mess = "新增普通用户账户")
    public Map<String, Object> addAccount(User user){
        String salt = RandomUtil.randomString(6);
        String Md5PWD = SecureUtil.md5(user.getPassword()+salt);
        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        user.setSalt(salt);
        user.setPassword(Md5PWD);
        user.setConfirmCode("管理员添加！默认激活！");
        user.setActivationTime(ldt);
        // 新增账号
        int result = adminMapper.insertUser(user);
        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0){
            String mes = "添加用户" + user.getEmail() + "成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }else {
            String mes = "添加用户" + user.getEmail() + "失败！";
            resultMap.put("code", 400);
            resultMap.put("message", mes);
        }
        return resultMap;
}

    /**
     * 删除用户信息
     * @param user 用户信息
     * @return 删除结果
     */
    @MyLog(role = "管理员", oper = "对用户账户操作", operType = "删除", mess = "删除普通用户账户")
    public Map<String, Object> delAccount(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        String username = adminMapper.getUsernameById(user.getId()).getEmail();
        int result = adminMapper.deleteUser(user);
        if(result > 0){
            String mes = "删除用户" + username + "成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }else {
            String mes = "删除用户" + username + "失败！";
            resultMap.put("code", 400);
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /**
     * 管理员账户登出
     * time: 2024/05/10 02：56
     * @param session 用户session
     * @return 登出结果
     */
    @MyLog(role = "管理员", oper = "账户操作", operType = "登出", mess = "管理员账号登出")
    public Map<String, String> logout(HttpSession session) {
        session.removeAttribute("adminId");
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "登出成功！");
        return response;
    }

    /**
     * 获取账号信息
     * time: 2024/05/10 03:09
     * @param session session
     * @return 账号信息
     */
    public User getInfo(HttpSession session) {
        Integer id = (Integer) session.getAttribute("adminId");
        return adminMapper.getUserInfo(id);
    }

    /**
     * 更改普通用户的密码
     * @param user 用户信息(ID、新密码)
     * @return 更改结果
     */
    @MyLog(role = "管理员", oper = "对用户账户操作", operType = "更改", mess = "更改普通用户账户的密码")
    public Map<String, Object> updatePwOfUser(User user) {
        HashMap<String, Object> resultMap = new HashMap<>();
        String salt = RandomUtil.randomString(6);
        String Md5PWD = SecureUtil.md5(user.getPassword()+salt);
        user.setSalt(salt);
        user.setPassword(Md5PWD);
        int result = adminMapper.updatePwOfUser(user);
        if (result > 0) {
            String mes = "修改用户" + user.getEmail() + "的密码成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }
        else {
            String mes = "修改用户" + user.getEmail() + "的密码失败！";
            resultMap.put("code", 400);
            resultMap.put("message",mes);
        }
        return resultMap;
    }

    /**
     * 更改普通用户的激活状态
     * @param user 用户信息（ID、isValid）
     * @return 更改结果
     */
    @MyLog(role = "管理员", oper = "对用户账户操作", operType = "更改", mess = "更改普通用户账户的状态")
    public Map<String, Object> updateStateOfUser(User user) {
        HashMap<String, Object> resultMap = new HashMap<>();
        int result = adminMapper.updateStateOfUser(user);
        if (result > 0) {
            String mes = "修改用户" + user.getEmail() + "的状态成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }
        else {
            String mes = "修改用户" + user.getEmail() + "的状态成功！";
            resultMap.put("code", 400);
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /* *********************
     **以下是销售人员管理模块***
     * *********************/

    /**
     * 获取所有销售人员列表
     * @return 销售人员列表
     */
    public List<User> getAllSalesmen() {
        return adminMapper.getAllSalesmen();
    }

    /**
     * 添加销售员
     * @param user 销售员账户信息
     * @return 添加结果
     */
    @MyLog(role = "管理员", oper = "对销售员账户操作", operType = "新增", mess = "新增销售员账户")
    public Map<String, Object> addSalesman(User user) {
        String salt = RandomUtil.randomString(6);
        String Md5PWD = SecureUtil.md5(user.getPassword()+salt);
        user.setActivationTime(LocalDateTime.now().plusDays(1));
        user.setSalt(salt);
        user.setPassword(Md5PWD);
        user.setConfirmCode("管理员添加！默认激活！");
        // 新增账号
        int result = adminMapper.insertSalesman(user);
        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0){
            String mes = "添加销售人员" + user.getEmail() + "成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }else {
            String mes = "添加销售人员" + user.getEmail() + "失败！";
            resultMap.put("code", 400);
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /**
     * 删除销售人员账户
     * @param user 账户信息
     * @return 删除结果
     */
    @MyLog(role = "管理员", oper = "对销售员账户操作", operType = "删除", mess = "删除销售员账户")
    public Map<String, Object> deleteSalesman(User user) {
        String username = adminMapper.getSalesmanNameById(user.getId()).getEmail();
        int result = adminMapper.deleteSalesman(user);
        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0){
            String mes = "删除销售人员" + username + "成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }else {
            String mes = "删除销售人员" + username + "失败！";
            resultMap.put("code", 400);
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /**
     * 更改销售人员的密码
     * @param user 销售人员信息
     * @return 更改结果
     */
    @MyLog(role = "管理员", oper = "对销售员账户操作", operType = "更改", mess = "更改销售人员账户密码")
    public Map<String, Object> updatePwOfSalesman(User user) {
        HashMap<String, Object> resultMap = new HashMap<>();
        String salt = RandomUtil.randomString(6);
        String Md5PWD = SecureUtil.md5(user.getPassword()+salt);
        user.setSalt(salt);
        user.setPassword(Md5PWD);
        int result = adminMapper.updatePwOfSalesman(user);
        if (result > 0) {
            String mes = "修改销售人员" + user.getEmail() + "的密码成功！";
            resultMap.put("code", 200);
            resultMap.put("message",mes);
        }
        else {
            String mes = "修改销售人员" + user.getEmail() + "的密码失败！";
            resultMap.put("code", 400);
            resultMap.put("message",mes);
        }
        return resultMap;
    }

    /**
     * 更改销售人员的激活状态
     * @param user 销售人员信息（ID、isValid）
     * @return 更改结果
     */
    @MyLog(role = "管理员", oper = "对销售员账户操作", operType = "更改", mess = "更改销售人员账户激活状态")
    public Map<String, Object> updateStateOfSalesman(User user) {
        HashMap<String, Object> resultMap = new HashMap<>();
        int result = adminMapper.updateStateOfSalesman(user);
        if (result > 0) {
            String mes = "修改销售人员" + user.getEmail() + "的状态成功！";
            resultMap.put("code", 200);
            resultMap.put("message", mes);
        }
        else {
            String mes = "修改销售人员" + user.getEmail() + "的状态失败！";
            resultMap.put("code", 400);
            resultMap.put("message", mes);
        }
        return resultMap;
    }
}
