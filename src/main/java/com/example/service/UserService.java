package com.example.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailService emailService;

    /**
     * 注册账号
     * @param user 用户信息
     * @return 注册结果
     */
    @MyLog(role = "用户", oper = "账户操作", operType = "注册", mess = "用户账户注册")
    @Transactional
    public Map<String, Object> createAccount(User user){
        // 雪花算法生成确认码
        String confirmCode = IdUtil.getSnowflake(1,1).nextIdStr();
        // 盐
        String salt = RandomUtil.randomString(6);
        // 加密
        String Md5PWD = SecureUtil.md5(user.getPassword()+salt);
        // 激活失效时间
        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        // 初始化账号信息
        user.setSalt(salt);
        user.setPassword(Md5PWD);
        user.setConfirmCode(confirmCode);
        user.setActivationTime(ldt);
        user.setIsValid((byte)0);
        // 新增账号
        int result = userMapper.insertUser(user);
        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message", "注册成功！请前往相应的邮箱进行账号激活，有效期<24小时内>");
            // 发送邮件
            String activationUrl = "http://localhost:8080/user/activation?confirmCode=" + confirmCode;
            emailService.sendMailForActivationAccount(activationUrl, user.getEmail());
        }else {
            resultMap.put("code", 400);
            resultMap.put("message", "注册失败！");
        }
        return resultMap;
    }

    /**
    * 登录账号
    * @param user 用户信息
    * @param session session
    * @return 登陆结果
    */
    @MyLog(role = "用户", oper = "账户操作", operType = "登录", mess = "用户账户登录")
    public Map<String, Object> loginAccount(User user, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        // 根据邮箱查询用户
        List<User> userList = userMapper.selectUserByEmail(user.getEmail());
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
        session.setAttribute("userID", u.getId());
        session.setAttribute("username", u.getEmail());
        session.setMaxInactiveInterval(15*60);
        return resultMap;
    }

    /**
     * 激活账户
     * @param confirmCode 确认码
     * @return Map 激活结果
     */
    @MyLog(role = "用户", oper = "账户操作", operType = "激活", mess = "用户账户激活")
    @Transactional
    public Map<String, Object> activationAccount(String confirmCode) {
        Map<String, Object> resultMap = new HashMap<>();
        // 根据确认码查询用户
        User user = userMapper.selectUserByConfirmCode(confirmCode);
        // 判断是否超时
        boolean after = LocalDateTime.now().isAfter(user.getActivationTime());
        if (after) {
            resultMap.put("code", 400);
            resultMap.put("message","激活链接已经失效，请重新注册！");
            return resultMap;
        }
        int result = userMapper.updateUserByConfirmCode(confirmCode);
        if (result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","激活成功！");
        }
        else{
            resultMap.put("code", 400);
            resultMap.put("message","激活失败！");
        }
        return resultMap;
    }

    /**
     * 根据session查询用户信息
     * @param session session
     * @return 返回用户信息
     */
    public User getInfo(HttpSession session) {
        Integer userID = (Integer)session.getAttribute("userID");
        return userMapper.getUserInfo(userID);
    }

    /**
     * 退出登录
     * @param session 用户session
     * @return 退出登录结果
     */
    @MyLog(role = "用户", oper = "账户操作", operType = "登出", mess = "用户账户登出")
    public Map<String, String> logout(HttpSession session) {
        session.removeAttribute("userID");
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "登出成功！");
        return response;
    }

}
