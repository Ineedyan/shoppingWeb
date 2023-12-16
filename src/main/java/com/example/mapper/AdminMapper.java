package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminMapper {
    /*
        新增账号
     */
    @Insert("INSERT INTO user(email, password, salt, confirm_code, activation_time, is_valid )" +
            "VALUES ( #{email}, #{password}, #{salt}, #{confirmCode}, #{activationTime}, #{isValid})")
    int insertUser(User user);

    /*
        搜索指定用户(已激活的)
     */
    @Select("SELECT id, email, password, salt FROM admin WHERE email = #{email} AND is_valid =1")
    List<User> selectUserByEmail(@Param("email") String email);

    /*
        搜索指定用户（包括未激活）
     */
    @Select("SELECT id, email, password, salt FROM user WHERE email = #{email}")
    List<User> selectUserByEmailAllStatus(@Param("email") String email);

    /*
        返回所有用户信息
     */
    @Select("SELECT * FROM user")
    List<User> getAllUser();

    /*
        删除账号
     */
    @Delete("DELETE FROM user WHERE email = #{email}")
    int deleteUser(User user);

    @Update("UPDATE user SET password = #{password}, is_valid = #{isValid} WHERE email = #{email}")
    int updateUser(User user);

}
