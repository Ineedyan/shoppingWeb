package com.example.mapper;

import com.example.pojo.OperExcpLog;
import com.example.pojo.OperLog;
import org.apache.ibatis.annotations.Insert;

public interface LogMapper {
    /**
     * 保存日志信息
     * @param operLog 日志信息
     */
    @Insert("INSERT INTO logs(role, username, operation, operationType, operationTime, ip, message, result)" +
            "VALUES (#{role}, #{username}, #{operation}, #{operationType}, #{operationTime}, #{ip}, #{message}, #{result})")
    void saveLog(OperLog operLog);

    /**
     * 保存异常日志信息
     * @param operExcpLog 异常日志信息
     */
    @Insert("INSERT INTO excplogs(methodName, role, username, operationTime, ip)" +
            "VALUES (#{methodName}, #{role}, #{username}, #{operationTime}, #{ip})")
    void saveExcpLog(OperExcpLog operExcpLog);
}
