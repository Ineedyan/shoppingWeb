# 学号：202130442426     姓名：郑耿彬
# 在线部署地址：http://8.138.115.234:8080/shoppingWeb

```
普通用户测试账号/密码： 761118187@qq.com / 123
销售人员测试账号/密码： admin / 123
                      sale11 / 123                    
管理员用户测试账号/密码：sadmin / 123
```

## 文档内容说明
src/main/resources/templates：前端html模板

src/main/java/com/example/controller：业务控制层接口类，负责接受前端请求，并调用Service层接口完成业务功能。

src/main/java/com/example/service：业务服务层，负责具体实现业务功能的逻辑，调用Mapper层接口完成与数据库的操作，并向前端返回响应信息，间接与数据库打交道

src/main/java/com/example/mapper：Mapper层，负责完成对数据库的插入、删除、修改、查询操作，返回操作结果供Service层使用

src/main/java/com/example/pojo：存放实体类定义，使用@Data注解。

