package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /*
        新增账号
     */
    @Insert("INSERT INTO user(email, password, salt, confirm_code, activation_time, is_valid )" +
            "VALUES ( #{email}, #{password}, #{salt}, #{confirmCode}, #{activationTime}, #{isValid})")
    int insertUser(User user);

    /*
    * 根据确认码查询用户
    */
    @Select("SELECT id, email, activation_time FROM user WHERE confirm_code = #{confirmCode} AND is_valid = 0 ")
    User selectUserByConfirmCode(@Param("confirmCode") String confirmCode);

    /*
    * 根据确认码查询用户并修改激活值为1（可用）
    */
    @Update("UPDATE user SET is_valid = 1 Where confirm_code = #{confirmCode}")
    int updateUserByConfirmCode(@Param("confirmCode") String confirmCode);

    // 根据邮箱查询用户信息
    @Select("SELECT id, email, password, salt FROM user WHERE email = #{email} AND is_valid =1")
    List<User> selectUserByEmail(@Param("email") String email);

    // 根据userID查询用户信息
    @Select("SELECT id, email from user WHERE id = #{userID}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
    })
    User getUserInfo(Integer userID);
}
