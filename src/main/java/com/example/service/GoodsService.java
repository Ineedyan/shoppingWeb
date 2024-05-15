package com.example.service;

import com.example.mapper.GoodsMapper;
import com.example.mapper.KindsMapper;
import com.example.mapper.SaleDataMapper;
import com.example.mapper.UserBuyMapper;
import com.example.pojo.Product;
import com.example.pojo.SaleData;
import com.example.pojo.UserBuy;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private KindsMapper kindsMapper;
    @Resource
    private UserBuyMapper userBuyMapper;
    @Resource
    private SaleDataMapper saleDataMapper;

    public List<Product> getAllProducts(){
        return goodsMapper.getAllProducts();
    }

    /**
     * 返回销售人员的商品
     * @param session session信息
     * @return 商品列表
     */
    public List<Product> getProductsBySalesman(HttpSession session) {
        return goodsMapper.getProductsBySalesman((String) session.getAttribute("salesmanName"));
    }

    /**
     * 显示商品信息
     * @param ID 商品id
     * @return 商品信息
     */
    public Product showGoodsInfo(Integer ID) {
        return goodsMapper.showProductByID(ID);
    }

    /**
     * 更新商品信息
     * @param product 商品信息
     * @return 更改结果
     */
    @MyLog(role = "销售人员", oper = "商品操作", operType = "修改", mess = "修改商品信息")
    public Map<String, Object> updateGoodsInfo(Product product) {
        // 获取商品类别ID
        product.setKindId(kindsMapper.getKindIdByName(product.getKind()));
        Map<String, Object> resultMap = new HashMap<>();
        // 修改信息
        int result1 = goodsMapper.updateGoodsInfo(product);
        // 修改销售数据中的商品信息
        int result2 = saleDataMapper.updateKind(product.getId(), product.getKindId());
        if(result1 > 0 && result2>0 ){
            resultMap.put("code", 200);
            String productTitle = product.getTitle();
            resultMap.put("message","修改商品：" + productTitle + " 成功！");
        }
        else {
            resultMap.put("code", 400);
            String productTitle = product.getTitle();
            resultMap.put("message","修改商品：" + productTitle + " 失败！");
        }
        return resultMap;
    }

    /**
     * 销售人员删除商品信息
     * @param product 商品信息
     * @return 删除结果
     */
    @MyLog(role = "销售人员", oper = "商品操作", operType = "删除", mess = "删除商品信息")
    public Map<String, Object> deleteGoodsInfo(Product product) {
        Map<String, Object> resultMap = new HashMap<>();
        String productTitle = goodsMapper.showProductByID(product.getId()).getTitle();
        int result = goodsMapper.deleteGoodsInfo(product);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","删除商品：" + productTitle + " 成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","删除商品：" + productTitle + " 失败！");
        }
        return resultMap;
    }


    /**
     * 销售人员添加商品信息
     * @param product 商品信息
     * @param session 销售人员session信息
     * @return 添加结果
     */
    @MyLog(role = "销售人员", oper = "商品操作", operType = "新增", mess = "新增商品信息")
    public Map<String, Object> addGoodsInfo(Product product, HttpSession session){
        product.setKindId(kindsMapper.getKindIdByName(product.getKind()));
        product.setSalesmanId((String) session.getAttribute("salesmanName"));
        Map<String, Object> resultMap = new HashMap<>();
        int result1 = goodsMapper.addGoodsInfo(product);
        // 同时在销售额统计表中插入一条数据
        SaleData saleData = new SaleData();
        saleData.setNum(0);
        saleData.setTotalSale(0.0);
        saleData.setSalesmanName(product.getSalesmanId());
        saleData.setKindId(product.getKindId());
        saleData.setProductId(product.getId());
        int result2 = saleDataMapper.addProductSaleData(saleData);
        if(result1 > 0 && result2 > 0){
            resultMap.put("code", 200);
            String productTitle = product.getTitle();
            resultMap.put("message","添加商品：" + productTitle + " 成功！");
        }
        else {
            resultMap.put("code", 400);
            String productTitle = product.getTitle();
            resultMap.put("message","添加商品：" + productTitle + " 失败！");
        }
        return resultMap;
    }


    /**
     * 根据关键词搜索商品
     * @param searchWord 关键词
     * @return 商品列表
     */
    public List<Product> searchGoods(String searchWord) {
        return goodsMapper.searchGoods(searchWord);
    }

    /**
     * 管理员删除商品信息
     * @param product 商品信息
     * @return 删除结果
     */
    @MyLog(role = "管理员", oper = "商品操作", operType = "删除", mess = "删除商品信息")
    public Map<String, Object> deleteByAdmin(Product product) {
        Map<String, Object> resultMap = new HashMap<>();
        String productTitle = goodsMapper.showProductByID(product.getId()).getTitle();
        int result = goodsMapper.deleteGoodsInfo(product);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message","删除商品：" + productTitle + "成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","删除商品：" + productTitle + "失败！");
        }
        return resultMap;
    }

    /**
     * 管理员更改商品信息 接口
     * @param product 商品信息
     * @return 更改结果
     */
    @MyLog(role = "管理员", oper = "商品操作", operType = "修改", mess = "修改商品信息")
    public Map<String, Object> updateByAdmin(Product product) {
        // 获取类别
        product.setKindId(kindsMapper.getKindIdByName(product.getKind()));
        Map<String, Object> resultMap = new HashMap<>();
        // 修改信息
        int result1 = goodsMapper.updateGoodsInfo(product);
        // 修改销售数据中的商品信息
        int result2 = saleDataMapper.updateKind(product.getId(), product.getKindId());
        if(result1 > 0 && result2>0 ){
            resultMap.put("code", 200);
            String productTitle = product.getTitle();
            resultMap.put("message","修改商品：" + productTitle + " 成功！");
        }
        else {
            resultMap.put("code", 400);
            String productTitle = product.getTitle();
            resultMap.put("message","修改商品：" + productTitle + " 失败！");
        }
        return resultMap;
    }

    /**
     * 管理员根据对象获取商品
     * @param object 获取对象
     * @return 商品列表
     */
    public List<Product> getGoodsBySalesman(String object) {
        return goodsMapper.getProductsBySalesman(object);
    }

    /**
     * 管理员根据商品类别获取商品
     * @param kind 商品类别
     * @return 商品列表
     */
    public List<Product> getGoodsByKind(String kind) {
        return goodsMapper.getProductsByKind(kind);
    }

    /**
     * 获取产品推荐
     * @param session 用户session
     * @return 产品推荐
     */
    public List<Product> getRecommend(HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        // 推荐结果
        List<Product> res = new ArrayList<>();
        // 获取用户购买过的类别清单
        List<UserBuy> userBuyList = userBuyMapper.getAllByUserid(userID);
        // 用户没购买过商品 直接随机推荐
        if(userBuyList.isEmpty()) {
            List<Product> getTwo = goodsMapper.getRandomRecommend();
            res.addAll(getTwo);
            return res;
        }
        // 用于推荐的商品类别
        List<Integer> kindIdList = new ArrayList<>();
        if(userBuyList.size()>5){
            // 使用Collections.sort方法进行排序，传入自定义的Comparator对象
            Collections.sort(userBuyList, new Comparator<UserBuy>() {
                @Override
                public int compare(UserBuy o1, UserBuy o2) {
                    // 按照count属性降序排序
                    return o2.getCount() - o1.getCount();
                }
            });
            for(int i=0;i<5;i++){
                kindIdList.add(userBuyList.get(i).getKindId());
            }
        }else{
            for (UserBuy userBuy : userBuyList) {
                kindIdList.add(userBuy.getKindId());
            }
        }
        // 创建一个Random对象
        Random random = new Random();
        // 生成一个随机索引，范围是从0到list.size() - 1
        int randomIndex1 = random.nextInt(kindIdList.size());
        int randomIndex2 = random.nextInt(kindIdList.size());
        // 根据随机索引获取对应的元素
        res.add(goodsMapper.getRecommendProduct(kindIdList.get(randomIndex1)));
        res.add(goodsMapper.getRecommendProduct(kindIdList.get(randomIndex2)));
        return res;
    }
}
