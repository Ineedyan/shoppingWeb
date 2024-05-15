package com.example.service;

import com.example.mapper.LogMapper;
import com.example.pojo.OperExcpLog;
import com.example.pojo.OperLog;
import com.example.utils.MyLog;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

@Component
@Aspect
public class LogService {

    @Autowired
    private LogMapper logmapper;

    /**
     * 自定义注解装饰（@MyLog）
     * 设置操作日志切入点
     */
    @Pointcut("@annotation(com.example.utils.MyLog)")
    public void operLogPointcut(){}

    /**
     * 设置操作异常日志切入点 扫描controller包下操作
     */
    @Pointcut("execution(* com.example.controller..*.*(..))")
    public void operExceptionLogPointCut(){}

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行
     * @param point 切入点
     * @param keys 返回值
     * 方法执行后调用
     */
    @AfterReturning(value = "operLogPointcut()", returning = "keys")
    public void saveOperLog(JoinPoint point, Object keys) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        OperLog operLog = new OperLog();
        try {
            // 从切面织入点获取织入店处的方法（反射）
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            MyLog myLog = method.getAnnotation(MyLog.class);
            if(myLog!=null){
                String oper = myLog.oper();
                String operType = myLog.operType();
                String mess = myLog.mess();
                String role = myLog.role();
                operLog.setRole(role);
                operLog.setOperation(oper);
                operLog.setOperationType(operType);
                operLog.setMessage(mess);
            }
            // 用户名
            if(operLog.getRole().equals("用户")) {
                String uname = (String) request.getSession().getAttribute("username");
                operLog.setUsername(uname);
            }else{
                if(operLog.getRole().equals("销售人员")){
                    String uname = (String) request.getSession().getAttribute("salesmanName");
                    operLog.setUsername(uname);
                }else{
                    String uname = (String) request.getSession().getAttribute("adminName");
                    operLog.setUsername(uname);
                }
            }
            // 时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar ca = Calendar.getInstance();
            String operDate = df.format(ca.getTime());
            operLog.setOperationTime(operDate);
            // ip
            String ip = getIp(request);
            ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
            operLog.setIp(ip);
            // 结果
            Map<String, Object> result = (Map<String, Object>) keys;
            operLog.setResult((String) result.get("message"));

        } catch (Exception e){
            e.printStackTrace();
        }
        // 保存到数据库嘞！
        logmapper.saveLog(operLog);
    }

    /**
     * 异常返回通知，拦截异常日志信息，连接点抛出异常后执行
     * @param joinPoint 切入点
     * @param e 异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        OperExcpLog operExcpLog = new OperExcpLog();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String className = joinPoint.getTarget().getClass().toString();
            String methodName = method.getName();
            operExcpLog.setMethodName(className + "-" + methodName);
            // 操作人员
            String role = (String) request.getSession().getAttribute("role");
            operExcpLog.setRole(role);
            String uname = (String) request.getSession().getAttribute("username");
            operExcpLog.setUsername(uname);
            // 时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar ca = Calendar.getInstance();
            String operDate = df.format(ca.getTime());
            operExcpLog.setOperationTime(operDate);
            // ip
            String ip = getIp(request);
            ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
            operExcpLog.setIp(ip);
        }catch (Exception e2){
            e2.printStackTrace();
        }
        logmapper.saveExcpLog(operExcpLog);
    }



    /**
     * 获取请求ip
     * @param request http请求
     * @return ip
     */
    public static String getIp(HttpServletRequest request){
        if(request.getHeader("x-forwarded-for") == null){
            return request.getRemoteAddr();
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
