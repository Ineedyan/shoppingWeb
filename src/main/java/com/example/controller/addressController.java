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

    /**
     * 根据用户获取收货地址信息
     * @param session 用户session
     * @return 地址列表
     */
    @PostMapping("getInfo")
    public List<Address> getAddressInfoByUser(HttpSession session){
        return addressService.getAddressInfoByUser(session);
    }

    /**
     * 添加地址
     * @param address 地址信息
     * @param session 用户session
     * @return 添加结果
     */
    @PostMapping("add")
    public Map<String,Object> addAddress(Address address,HttpSession session){
        return addressService.addAddress(address, session);
    }

    /**
     * 获取单条地址信息
     * @param id 地址id
     * @return 地址信息
     */
    @PostMapping("fillForm")
    public Address getAddressInfoByID(String id){
        return addressService.getAddressInfoByID(id);
    }

    /**
     * 地址信息更新
     * @param address 新地址信息
     * @return 更新结果
     */
    @PostMapping("update")
    public Map<String,Object> updateAddressInfo(Address address){
        return addressService.updateAddressInfo(address);
    }

    /**
     * 地址信息删除
     * @param address 地址id
     * @return 删除结果
     */
    @PostMapping("delete")
    public Map<String,Object> deleteAddressByID(Address address){
        return addressService.deleteAddressByID(address);
    }


}
