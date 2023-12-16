package com.example.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.mapper.AdminMapper;
import com.example.pojo.User;
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

    /*
    * 登录账号
    * @param user
    * @return
    */
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
        session.setMaxInactiveInterval(2*60);
        return resultMap;
    }

    /*
     * 返回所有用户
     * */
    public List<User> getAllUsers(){
        return adminMapper.getAllUser();
    }

    /*
    * 添加用户
    * */
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
            resultMap.put("code", 200);
            resultMap.put("message", "添加用户成功！");
        }else {
            resultMap.put("code", 400);
            resultMap.put("message", "注册失败！");
        }
        return resultMap;
}

    /*
     * 删除用户
     * */
    public Map<String, Object> delAccount(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = adminMapper.deleteUser(user);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message", "删除用户成功！");
        }else {
            resultMap.put("code", 400);
            resultMap.put("message", "删除失败！");
        }
        return resultMap;
    }

    /*
     * 更新用户
     * */
    public Map<String, Object> updateAccount(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        List<User> userList = adminMapper.selectUserByEmailAllStatus(user.getEmail());
        // 查询不到用户，返回不存在或未激活
        if (userList == null || userList.isEmpty()) {
            resultMap.put("code", 400);
            resultMap.put("message","该账户不存在!");
            return resultMap;
        }
        // 查询到多个，返回账号异常
        if (userList.size()>1) {
            resultMap.put("code", 400);
            resultMap.put("message","账户异常！存在多个同账户用户");
            return resultMap;
        }
        // 查询到一个用户，进行修改
        User u = userList.get(0);
        String Md5PWD = SecureUtil.md5(user.getPassword()+u.getSalt());
        u.setPassword(Md5PWD);
        u.setIsValid(user.getIsValid());
        int result = adminMapper.updateUser(u);
        if (result > 0) {
            resultMap.put("code", 200);
            resultMap.put("message","修改成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","修改失败！");
        }
        return resultMap;
    }


}
