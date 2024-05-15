package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /**
     * 新增账户
     * @param user 账户信息
     * @return 添加结果
     */
    @Insert("INSERT INTO user(email, password, salt, confirm_code, activation_time, is_valid )" +
            "VALUES ( #{email}, #{password}, #{salt}, #{confirmCode}, #{activationTime}, #{isValid})")
    int insertUser(User user);

    /**
     * 根据确认码查询用户
     * @param confirmCode 确认码
     * @return 查询结果
     */
    @Select("SELECT id, email, activation_time FROM user WHERE confirm_code = #{confirmCode} AND is_valid = 0 ")
    User selectUserByConfirmCode(@Param("confirmCode") String confirmCode);

    /**
     * 用户账户激活
     * @param confirmCode 确认码
     * @return 激活结果
     */
    @Update("UPDATE user SET is_valid = 1 Where confirm_code = #{confirmCode}")
    int updateUserByConfirmCode(@Param("confirmCode") String confirmCode);

    /**
     * 根据邮箱查询用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    @Select("SELECT id, email, password, salt, is_valid FROM user WHERE email = #{email}")
    List<User> selectUserByEmail(@Param("email") String email);

    /**
     * 根据userID获取基本信息
     * @param userID userID
     * @return 基本信息（id、email）
     */
    @Select("SELECT id, email from user WHERE id = #{userID}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
    })
    User getUserInfo(Integer userID);

    /**
     * 根据用户ID查询用户名
     * @param userId 用户id
     * @return 用户名
     */
    @Select("SELECT email FROM user WHERE id = #{userId}")
    String getUserNameByID(Integer userId);
}
