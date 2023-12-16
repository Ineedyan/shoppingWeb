package com.example.controller;


import com.example.pojo.Address;
import com.example.service.AddressService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("address")
public class addressController {
    @Resource
    private AddressService addressService;

    // 获取收货地址信息
    @PostMapping("getInfo")
    public List<Address> getAddressInfoByUser(HttpSession session){
        return addressService.getAddressInfoByUser(session);
    }

    // 添加地址信息
    @PostMapping("add")
    public Map<String,Object> addAddress(Address address,HttpSession session){
        return addressService.addAddress(address, session);
    }

    // 获取单条地址信息并返回到表单
    @PostMapping("fillForm")
    public Address getAddressInfoByID(String id){
        return addressService.getAddressInfoByID(id);
    }

    // 更新地址信息
    @PostMapping("update")
    public Map<String,Object> updateAddressInfo(Address address){
        return addressService.updateAddressInfo(address);
    }

    // 删除地址信息
    @PostMapping("delete")
    public Map<String,Object> deleteAddressByID(Address address){
        return addressService.deleteAddressByID(address);
    }


}
