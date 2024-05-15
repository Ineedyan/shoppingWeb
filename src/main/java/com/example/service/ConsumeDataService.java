package com.example.service;

import com.example.mapper.*;
import com.example.pojo.ConsumeData;
import com.example.pojo.Order;
import com.example.pojo.ProductKind;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsumeDataService {
    @Resource
    private ConsumeDataMapper consumeDataMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserBuyMapper userBuyMapper;
    @Resource
    private KindsMapper kindsMapper;

    /**
     * 获取用户消费表中数据
     * @return 用户消费数据
     */
    public List<ConsumeData> getAllData() {
        return consumeDataMapper.getAllData();
    }

    /**
     * 分析用户消费数据 接口
     * @return 分析成功与否结果
     */
    @MyLog(role = "管理员", oper = "消费数据操作", operType = "分析", mess = "分析用户消费数据")
    public Map<String, Object> analyseData() {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取所有订单信息
        List<Order> orderList = orderMapper.getAllOrder();
        // 无订单 返回信息
        if (orderList.isEmpty()){
            resultMap.put("code", 400);
            resultMap.put("message","无用户消费数据！");
            return resultMap;
        }
        // orderNumMap: userID -> orderNum
        // totalPriceMap: userID -> totalPrice
        HashMap<Integer, Integer> orderNumMap = new HashMap<>();
        HashMap<Integer, Double> totalPriceMap = new HashMap<>();
        for (Order order : orderList) {
            // 获取订单用户ID
            int userId = order.getUserId();
            // 统计订单数
            int orderNum = orderNumMap.getOrDefault(userId, 0);
            orderNumMap.put(userId, orderNum + 1);
            // 统计总消费金额
            double price = totalPriceMap.getOrDefault(userId, (Double) 0.0);
            totalPriceMap.put(userId, price + order.getTotalPrice());
        }
        List<ConsumeData> consumeDataList = new ArrayList<>();
        for(Integer key: orderNumMap.keySet()){
            // 获取用户名
            String username = userMapper.getUserNameByID(key);
            // 获取用户购买最多的类别
            List<Integer> kindIdList = userBuyMapper.getMostKindByUserid(key);
            StringBuilder kindmost = new StringBuilder();
            for (Integer integer : kindIdList) {
                kindmost.append(kindsMapper.getKindById(new ProductKind(integer)));
                kindmost.append(";");
            }
            // 初始化消费数据
            ConsumeData consumeData = new ConsumeData();
            consumeData.setUserId(key);
            consumeData.setUsername(username);
            consumeData.setOrderNum(orderNumMap.get(key));
            consumeData.setTotalPrice(totalPriceMap.get(key));
            consumeData.setAvgPrice(totalPriceMap.get(key)/orderNumMap.get(key));
            consumeData.setKindMost(kindmost.toString());
            // 插入列表
            consumeDataList.add(consumeData);
        }
        // 清空表
        consumeDataMapper.deleteAllData();
        // 批量插入
        int result = consumeDataMapper.insertListData(consumeDataList);
        if(result>0){
            resultMap.put("code", 200);
            resultMap.put("message","用户消费数据分析成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","用户消费数据分析失败！");
        }
        return resultMap;
    }
}
