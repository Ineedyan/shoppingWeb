package com.example.controller;


import com.example.pojo.OperLog;
import com.example.service.LogsInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logs")
public class logsController {
    @Resource
    private LogsInfoService logsInfoService;

    /**
     * 获取所有日志信息 接口
     * @return 所有日志信息
     */
    @GetMapping("getAllLogs")
    public List<OperLog> getAllLogs(){
        return logsInfoService.getAllLogs();
    }

    /**
     * 根据角色模块获取日志信息 接口
     * @param object 角色（用户/销售员/管理员）
     * @return 日志信息
     */
    @PostMapping("getLogsByRole")
    public List<OperLog> getLogsByRole(@RequestParam(value = "object") String object){
        return logsInfoService.getLogsByRole(object);
    }
}
