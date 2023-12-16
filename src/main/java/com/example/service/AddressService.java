package com.example.service;

import com.example.mapper.AddressMapper;
import com.example.pojo.Address;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    @Resource
    private AddressMapper addressMapper;

    // 根据userID获取地址信息
    public List<Address> getAddressInfoByUser(HttpSession session) {
        Integer userID = (Integer)session.getAttribute("userID");
        return addressMapper.getAddressInfoByUser(userID);
    }

    // 删除地址信息
    public Map<String, Object> deleteAddressByID(Address address) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = addressMapper.deleteAddressByID(address);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","删除成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","删除失败！");
        }
        return resultMap;
    }

    // 添加地址信息
    public Map<String, Object> addAddress(Address address, HttpSession session) {
        Integer userID = (Integer)session.getAttribute("userID");
        Map<String, Object> resultMap = new HashMap<>();
        if(userID == null){
            resultMap.put("code", 400);
            resultMap.put("message","未登录！添加失败");
            return resultMap;
        }
        address.setUserId(userID);
        int result = addressMapper.addAddress(address);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","添加成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","添加失败！");
        }
        return resultMap;
    }

    // 根据id获取地址信息
    public Address getAddressInfoByID(String id) {
        return addressMapper.getAddressInfoByID(id);
    }

    public Map<String, Object> updateAddressInfo(Address address) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = addressMapper.updateAddressInfo(address);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","修改成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","修改失败！");
        }
        return resultMap;
    }
}
