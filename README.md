[开启邮箱客户端]: https://blog.csdn.net/baolingye/article/details/96598222
参照教程，修改application.properties文件中的email相关参数：
修改为自己的参数
```properties
email.client.address=邮箱地址
email.client.account-id=qq号
email.client.auth-password=密钥
```
继续往下，配置数据库
打开数据库连接
```sql
create schema gxrsms character_set_name utf8mb4;
```
找到
```properties
spring.datasource.initialization-mode=never
```
把值改为always,
数据库信息改为自己的。
配置web文件位置：  
左上角file-->project structure-->facets-->WEB(web)-->web resources directories-->点右边+ -->选中web木块下的src/main/web文件夹  
apply +ok。
找到applicationRun类，运行
浏览器输入localhost:9999/gxrsms/回车
初始用户名admin,密码：123456
数据库看密码是字符串是因为用了shiro框架，md5加密。