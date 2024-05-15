package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SalesmanMapper {

    /**
     * 根据邮箱查询账户信息
     * @param email 邮箱
     * @return 账户信息
     */
    @Select("SELECT id, email, password, salt, is_valid FROM salesman WHERE email = #{email}")
    List<User> selectUserByEmail(@Param("email") String email);

    /**
     * 获取销售人员账户基本信息
     * @param id 销售人员id
     * @return 基本信息（id、email）
     */
    @Select("SELECT id, email from salesman WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
    })
    User getUserInfo(Integer id);
}
