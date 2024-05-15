package com.example.service;

import com.example.mapper.*;
import com.example.pojo.*;
import com.example.utils.MyLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    private CartService cartService;
    @Resource
    private EmailService emailService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private UserBuyMapper userBuyMapper;
    @Resource
    private SaleDataMapper saleDataMapper;
    /**
     * 创建订单
     * @param order 订单信息
     * @param session 用户session
     * @return 创建订单结果
     */
    @MyLog(role = "用户", oper = "订单操作", operType = "新增", mess = "创建用户订单")
    // 创建订单
    public Map<String, Object> createOrder(Order order, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取用户ID
        Integer useID = (Integer) session.getAttribute("userID");
        if(useID == null){
            resultMap.put("code", 400);
            resultMap.put("message","操作失败！未登录或长时间未操作！请重新登录");
        }
        // 获取用户购物车详情
        List<detailCart> detailCartList = cartService.showRecordFromCart(session);
        // 异常商品列表
        List<String> errorList = new ArrayList<>();
        // 是否有商品库存不足
        for(detailCart dc:detailCartList){
            Integer amount = goodsMapper.getAmountOfProduct(dc.getProductId());
            if(amount<dc.getAmount()){
                errorList.add(dc.getTitle());
            }
        }
        if(!errorList.isEmpty()) {
            resultMap.put("code", 400);
            StringBuffer mes = new StringBuffer("下单失败！以下商品库存不足：");
            for (String s : errorList) {
                mes.append(s).append(" ");
            }
            resultMap.put("message", mes.toString());
            return resultMap;
        }

        // 设置映射 Map：salesmanName - > detailCartList
        // 设置映射 KindMap：kindId -> num
        HashMap<String, List<detailCart>> map = new HashMap<>();
        HashMap<Integer, Integer> kindMap = new HashMap<>();
        for (com.example.pojo.detailCart item : detailCartList) {
            // 将订单按商家分类
            String salesman = item.getSalesmanName();
            List<detailCart> tmp = map.getOrDefault(salesman, new ArrayList<>());
            tmp.add(item);
            map.put(salesman, tmp);
            // 将订单按类别分类
            Integer kindId = item.getKindId();
            kindMap.put(kindId, kindMap.getOrDefault(kindId, 0)+1);
        }
        // 设置订单时间
        order.setOrderTime(LocalDateTime.now());
        // 设置用户ID
        order.setUserId(useID);
        // 设置订单状态
        order.setState("未发货");
        // 设置快递单号
        order.setExpressNumber("");
        // 设置地址
        String fullAddress = addressMapper.getFullAddressInfoByID(order.getAddress());
        order.setAddress(fullAddress);
        // 对属于不同商家的商品进行分订单处理
        for(String key: map.keySet()){
            // 获取商家
            order.setSalesmanName(key);
            // 获取商家下的订单
            List<detailCart> tmp = map.get(key);
            // 计算订单价格总和
            double totalPriceSum = tmp.stream()
                    .mapToDouble(item -> item.getFinalPrice() * item.getAmount())
                    .sum();
            order.setTotalPrice(totalPriceSum);
            // 修改商品库存
            for(detailCart dc:tmp){
                Integer amount = goodsMapper.getAmountOfProduct(dc.getProductId());
                goodsMapper.updateNumOfProduct(dc.getProductId(), amount-dc.getAmount());
            }
            // 创建订单
            int result1 = orderMapper.createOrder(order);
            // 储存订单具体信息
            for(detailCart cartItem:tmp){
                OrderItem orderItem = new OrderItem();
                // 设置订单中具体的商品信息
                orderItem.setOrderId(order.getId());
                orderItem.setProductId(cartItem.getProductId());
                orderItem.setAmount(cartItem.getAmount());
                orderItem.setTotalPrice(cartItem.getAmount()* cartItem.getFinalPrice());
                orderItem.setTitle(cartItem.getTitle());
                int result2 = orderMapper.recordOrder(orderItem);
                if(result2 <=0){
                    resultMap.put("code", 400);
                    resultMap.put("message","下单失败！");
                    return resultMap;
                }
            }
            if(result1 > 0){
                resultMap.put("code", 200);
                resultMap.put("message","下单成功！已发送邮件通知用户！");
            }
            else {
                resultMap.put("code", 400);
                resultMap.put("message","下单失败！");
                return resultMap;
            }
        }
        // 发送下单成功的邮件
        User user = userMapper.getUserInfo(useID);
        emailService.sendMailForBuy(user.getEmail());

        // 统计购买的商品类别
        for(Integer key: kindMap.keySet()){
            Integer count = userBuyMapper.getCountByUserAndKind(useID, key);
            if(count==null){
                count = 0;
                UserBuy userBuy = new UserBuy();
                userBuy.setUserId(useID);
                userBuy.setCount(kindMap.get(key));
                userBuy.setKindId(key);
                userBuyMapper.insertCount(userBuy);
            }
            userBuyMapper.updateCountByUserAndKind(useID, key, count+kindMap.get(key));
        }
        return resultMap;

    }

    /**
     * 根据用户ID查询订单
     * @param session session信息
     * @return 订单信息
     */
    public List<Order> searchOrderByID(HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");
        return orderMapper.searchOrderByID(userID);
    }

    /**
     * 查询订单详情
     * @param orderId 订单id
     * @return 订单详情
     */
    public List<OrderItem> getOrderDetails(Integer orderId) {
        return orderMapper.getOrderDetails(orderId);
    }

    /**
     * 获取所有订单
     * @return 所有订单信息
     */
    public List<Order> getAllOrder() {
        return orderMapper.getAllOrder();
    }

    /**
     * 根据销售人员获取订单
     * @param session 销售人员的session信息
     * @return 订单列表
     */
    public List<Order> getOrdersBySalesman(HttpSession session) {
        return orderMapper.getOrdersBySalesman((String) session.getAttribute("salesmanName"));
    }

    /**
     * 根据指定对象获取订单接口
     * @param object 获取对象
     * @return 订单信息
     */
    public List<Order> getOrdersByObject(String object){
        return orderMapper.getOrdersByObject(object);
    }

    /**
     * 订单发货与统计销售额
     * @param orderId 订单ID
     * @param expressNumber 快递单号
     * @return 发货结果
     */
    @MyLog(role = "销售人员", oper = "订单操作", operType = "发货", mess = "发货订单")
    public Map<String, Object> delivery(Integer orderId, String expressNumber) {
        Map<String, Object> resultMap = new HashMap<>();
        // 查询一下订单状态，如果已发货，不可重复发货
        String state = orderMapper.getOrderStateByOrderID(orderId);
        if(state.equals("已发货")){
            resultMap.put("code", 400);
            resultMap.put("message", "发货失败！不可重复发货！");
            return resultMap;
        }
        // 修改订单状态及快递单号
        int result = orderMapper.delivery(orderId, "已发货", expressNumber);
        // 统计商品销售额
        List<OrderItem> orderItemList = orderMapper.getOrderDetails(orderId);
        for(OrderItem item:orderItemList){
            Integer productId = item.getProductId();
            Integer num = item.getAmount();
            Double totalPrice = item.getTotalPrice();
            SaleData saleData = saleDataMapper.getDataByProductId(productId);
            saleData.setTotalSale(saleData.getTotalSale() + totalPrice);
            saleData.setNum(saleData.getNum() + num);
            // 更新数据
            saleDataMapper.updateData(saleData);
        }
        if(result > 0){
            resultMap.put("code", 200);
            String mes = "订单ID：" + orderId + "发货成功！";
            resultMap.put("message", mes);
        }
        else {
            resultMap.put("code", 400);
            String mes = "订单ID：" + orderId + "发货失败！";
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /**
     * 终止订单并更新销售数据
     * @param orderId 订单ID
     * @return 终止结果
     */
    @MyLog(role = "销售人员", oper = "订单操作", operType = "终止", mess = "终止订单")
    public Map<String, Object> endOrder(Integer orderId) {
        Map<String, Object> resultMap = new HashMap<>();
        // 查询一下订单状态，如果已发货，不可重复发货
        String state = orderMapper.getOrderStateByOrderID(orderId);
        if(state.equals("订单终止！请联系商家！")){
            resultMap.put("code", 400);
            resultMap.put("message", "终止失败！不可重复终止订单！");
            return resultMap;
        }
        int result = orderMapper.endOrder(orderId, "订单终止！请联系商家！");
        // 更改商品销售额
        List<OrderItem> orderItemList = orderMapper.getOrderDetails(orderId);
        for(OrderItem item:orderItemList){
            Integer productId = item.getProductId();
            Integer num = item.getAmount();
            Double totalPrice = item.getTotalPrice();
            SaleData saleData = saleDataMapper.getDataByProductId(productId);
            saleData.setTotalSale(saleData.getTotalSale() - totalPrice);
            saleData.setNum(saleData.getNum() - num);
            // 更新数据
            saleDataMapper.updateData(saleData);
        }
        if(result > 0){
            resultMap.put("code", 200);
            String mes = "订单ID：" + orderId + "终止成功！";
            resultMap.put("message",mes);
        }
        else {
            resultMap.put("code", 400);
            String mes = "订单ID：" + orderId + "终止失败！";
            resultMap.put("message", mes);
        }
        return resultMap;
    }

    /**
     * 删除订单
     * @param orderId 订单ID
     * @return 删除结果
     */
    @MyLog(role = "管理员", oper = "订单操作", operType = "删除", mess = "删除订单")
    public Map<String, Object> deleteOrder(Integer orderId) {
        Map<String, Object> resultMap = new HashMap<>();

        int result1 = orderMapper.deleteOrder(orderId);
        if(result1 > 0){
            resultMap.put("code", 200);
            resultMap.put("message","删除订单" + orderId + "成功！");
        }
        else {
            resultMap.put("code", 400);
            resultMap.put("message","删除订单" + orderId + "失败！");
        }
        return resultMap;
    }
}
