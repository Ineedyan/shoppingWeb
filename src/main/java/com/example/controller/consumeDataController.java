package com.example.controller;

import com.example.pojo.ConsumeData;
import com.example.service.ConsumeDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class consumeDataController {
    @Resource
    private ConsumeDataService consumeDataService;

    /**
     * 获取用户消费表中数据 接口
     * @return 用户消费数据
     */
    @GetMapping ("/getAllData")
    public List<ConsumeData> getAllData(){
        return consumeDataService.getAllData();
    }

    /**
     * 分析用户消费数据 接口
     * @return 分析成功与否结果
     */
    @PostMapping("/analyseData")
    public Map<String, Object> analyseData(){
        return consumeDataService.analyseData();
    }
}
