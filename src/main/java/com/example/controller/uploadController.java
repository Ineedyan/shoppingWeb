package com.example.controller;

import com.example.pojo.Product;
import com.example.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class uploadController {
    @Resource
    private GoodsService goodsService;

    @RequestMapping("imgStr")
    public Map<String, Object> ImgStr(@RequestParam("file") MultipartFile file, Product product)throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        //我们简单验证一下file文件是否为空
        if (file.isEmpty()){
            resultMap.put("code", 400);
            resultMap.put("message","上传失败！");
            return resultMap;}
        Date date = new Date();
        //获取当前系统时间年月这里获取到月如果要精确到日修改("yyyy-MM-dd")
        String dateForm = new SimpleDateFormat("yyyy-MM").format(date);
        //地址合并 path.getFileimg
        String casePath = "/home/imgUpload/" +dateForm;
        //获取图片后缀
        String imgFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //判断文件是否存在
        File f = new File(casePath);
        try {if (!f.exists()){f.mkdirs();}
        }catch (Exception e){
            resultMap.put("code", 400);
            resultMap.put("message","上传失败！");
            return resultMap;
        }
        //给图片重新随机生成名字
        String name= UUID.randomUUID().toString()+ imgFormat;
        //保存图片
        file.transferTo(new File(casePath+"/"+name));
        //拼接要保存在数据中的图片地址
        //path.getUREIMG() 同样也是存放在实体类的字段 可以直接写 http://localhost:8080/
        //dateForm 这是动态的文件夹所以要和地址一起存入数据库中
        String urlImg = "/img/"+dateForm+"/"+name;
        //放入对应的字段中
        product.setImg(urlImg);
        //上传数据库
        return goodsService.addGoodsInfo(product);
    }

}
