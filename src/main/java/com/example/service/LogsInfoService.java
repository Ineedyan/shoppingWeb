package com.example.service;

import com.example.mapper.LogsInfoMapper;
import com.example.pojo.OperLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogsInfoService {
    @Resource
    private LogsInfoMapper logsInfoMapper;

    /**
     * 获取所有日志信息
     * @return 日志信息列表
     */
    public List<OperLog> getAllLogs() {
        return logsInfoMapper.getAllLogs();
    }

    /**
     * 根据角色模块获取日志信息
     * @param object 角色（用户/销售员/管理员）
     * @return 日志信息
     */
    public List<OperLog> getLogsByRole(String object) {
        if(object.equals("user")){
            return logsInfoMapper.getLogsByRole("用户");
        }else if (object.equals("salesman")){
            return logsInfoMapper.getLogsByRole("销售人员");
        }else if(object.equals("admin")){
            return logsInfoMapper.getLogsByRole("管理员");
        }
        return logsInfoMapper.getAllLogs();
    }
}
