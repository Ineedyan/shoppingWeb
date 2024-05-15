package com.example.mapper;

import com.example.pojo.UserBuy;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserBuyMapper {
    /**
     * 根据用户和类别ID查询购买数量
     * @param useID 用户id
     * @param kindId 类别id
     * @return 购买次数
     */
    @Select("SELECT count FROM userbuy WHERE userId = #{useID} AND kindId = #{kindId}")
    Integer getCountByUserAndKind(Integer useID, Integer kindId);

    /**
     * 更新购买数量
     * @param useID 用户id
     * @param kindId 类别id
     * @param count 新购买数量
     */
    @Update("UPDATE userbuy SET count = #{count} WHERE userId = #{useID} AND kindId = #{kindId}")
    void updateCountByUserAndKind(Integer useID, Integer kindId, Integer count);

    /**
     * 插入用户购买数据
     * @param userBuy 购买信息（用户ID、类别ID、购买数量）
     */
    @Insert("INSERT INTO userbuy(userId, kindId, count) " +
            "VALUES (#{userId}, #{kindId}, #{count})")
    void insertCount(UserBuy userBuy);

    /**
     * 获取用户购买过的类别及其数量
     * @param userID 用户ID
     * @return （类别+数量）的列表
     */
    @Select("SELECT kindId, count FROM userbuy WHERE userId = #{userID}")
    @Results({
            @Result(property = "kindId", column = "kindId"),
            @Result(property = "count", column = "count")
    })
    List<UserBuy> getAllByUserid(Integer userID);

    /**
     * 根据用户ID获取购买最多的商品类别ID
     * @param userID 用户id
     * @return 商品类别id
     */
    @Select("SELECT kindId FROM userbuy WHERE count = (" +
                "SELECT MAX(count) WHERE userId = #{userID})")
    List<Integer> getMostKindByUserid(Integer userID);
}
