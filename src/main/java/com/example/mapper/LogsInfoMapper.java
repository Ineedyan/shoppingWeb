package com.example.mapper;

import com.example.pojo.OperLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LogsInfoMapper {
    /**
     * 获取所有日志信息
     * @return 日志信息列表
     */
    @Select("SELECT * FROM logs")
    List<OperLog> getAllLogs();

    /**
     * 根据角色获取日志信息
     * @param role 角色
     * @return 日志信息
     */
    @Select("SELECT * FROM logs WHERE role = #{role}")
    List<OperLog> getLogsByRole(String role);
}
