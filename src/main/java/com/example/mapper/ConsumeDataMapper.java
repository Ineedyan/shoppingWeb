package com.example.mapper;

import com.example.pojo.ConsumeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConsumeDataMapper {

    /**
     * 获取用户消费表中数据
     * @return 用户消费数据
     */
    @Select("SELECT * FROM consumedata")
    List<ConsumeData> getAllData();

    /**
     * 批量插入数据
     * @param consumeDataList 消费数据列表
     */
    int insertListData(List<ConsumeData> consumeDataList);

    /**
     * 清空表的数据
     */
    void deleteAllData();
}
