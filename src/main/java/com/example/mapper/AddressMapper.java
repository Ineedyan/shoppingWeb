package com.example.mapper;

import com.example.pojo.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AddressMapper {
    // 根据userID获取user信息
    @Select("SELECT * from address WHERE userId = #{userID}")
    List<Address> getAddressInfoByUser(Integer userID);

    // 根据id删除地址信息
    @Delete("DELETE FROM address WHERE id = #{id}")
    int deleteAddressByID(Address address);

    // 添加地址信息
    @Insert("INSERT INTO address(userId, province, city, district, detailArea, name, phoneNum)" +
            "VALUES (#{userId},#{province},#{city},#{district},#{detailArea},#{name},#{phoneNum})")
    int addAddress(Address address);

    // 获取单条地址信息（根据ID）
    @Select("SELECT * FROM address WHERE id=#{id}")
    @Results({
            @Result(property = "detailArea", column = "detailArea"),
            @Result(property = "name", column = "name"),
            @Result(property = "phoneNum", column = "phoneNum"),
    })
    Address getAddressInfoByID(String id);

    // 获取地址全信息（根据ID）
    @Select("SELECT CONCAT(province, ' ', city, ' ', district, ' ', detailArea, ' - ', name, ' ', phoneNum)" +
            "FROM address WHERE id=#{id}")
    String getFullAddressInfoByID(String id);

    // 修改地址信息
    @Update("UPDATE address SET detailArea=#{detailArea},name=#{name},phoneNum=#{phoneNum} WHERE id=#{id} ")
    int updateAddressInfo(Address address);
}
