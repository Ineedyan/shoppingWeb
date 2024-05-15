package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminMapper {
    /**
     * 管理员新增普通用户账户
     * @param user 账户信息
     * @return 新增结果
     */
    @Insert("INSERT INTO user(email, password, salt, confirm_code, activation_time, is_valid )" +
            "VALUES ( #{email}, #{password}, #{salt}, #{confirmCode}, #{activationTime}, #{isValid})")
    int insertUser(User user);

    /**
     * 管理员登录时搜索账户信息
     * @param email 用户名
     * @return 账户信息
     */
    @Select("SELECT id, email, password, salt FROM admin WHERE email = #{email} AND is_valid =1")
    List<User> selectUserByEmail(@Param("email") String email);

    /**
     * 搜索全部状态的账户信息
     * @param email 用户名
     * @return 账户信息
     */
    @Select("SELECT id, email, password, salt FROM user WHERE email = #{email}")
    List<User> selectUserByEmailAllStatus(@Param("email") String email);

    /**
     * 返回所有用户信息
     * @return 所有用户信息列表
     */
    @Select("SELECT * FROM user")
    List<User> getAllUser();

    /**
     * 根据ID删除用户信息
     * @param user 用户信息
     * @return 删除结果
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(User user);

    /**
     * 获取管理员账户id和用户名信息
     * @param id 管理员id
     * @return 账号信息
     */
    @Select("SELECT id, email from admin WHERE id = #{id}")
    User getUserInfo(Integer id);

    /**
     * 根据用户ID获取用户名（email）
     * @param id 用户ID
     * @return 用户名
     */
    @Select("SELECT email FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "email", column = "email")
    })
    User getUsernameById(Integer id);

    /**
     * 根据ID更新用户的密码和盐
     * @param user 用户信息（id/new password/salt）
     * @return 更改结果
     */
    @Update("UPDATE user SET password = #{password}, salt = #{salt} WHERE id = #{id}")
    int updatePwOfUser(User user);

    /**
     * 根据ID更新用户的激活状态
     * @param user 用户信息（id/isValid）
     * @return 更改结果
     */
    @Update("UPDATE user SET is_valid = #{isValid} WHERE id = #{id}")
    int updateStateOfUser(User user);


    /**
     * 获取所有销售人员信息列表
     * @return 销售人员信息列表
     */
    @Select("SELECT * FROM salesman")
    List<User> getAllSalesmen();

    /**
     * 添加销售员账户
     * @param user 账户信息
     * @return 添加结果
     */
    @Insert("INSERT INTO salesman(email, password, salt, confirm_code, activation_time, is_valid )" +
            "VALUES ( #{email}, #{password}, #{salt}, #{confirmCode}, #{activationTime}, #{isValid})")
    int insertSalesman(User user);

    /**
     * 删除销售人员账户
     * @param user 账户信息
     * @return 添加结果
     */
    @Delete("DELETE FROM salesman WHERE id = #{id}")
    int deleteSalesman(User user);

    /**
     * 更改销售人员账户密码
     * @param user 账户信息
     * @return 更改结果
     */
    @Update("UPDATE salesman SET password = #{password}, salt = #{salt} WHERE id = #{id}")
    int updatePwOfSalesman(User user);

    /**
     * 更改销售人员账户状态
     * @param user 账户信息
     * @return 更改结果
     */
    @Update("UPDATE salesman SET is_valid = #{isValid} WHERE id = #{id}")
    int updateStateOfSalesman(User user);

    /**
     * 根据ID获取销售人员名
     * @param id 销售人员id
     * @return 销售人员名
     */
    @Select("SELECT email FROM salesman WHERE id = #{id}")
    @Results({
            @Result(property = "email", column = "email")
    })
    User getSalesmanNameById(Integer id);
}
