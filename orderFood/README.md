## 本项目完整源码是收费的  接毕业设计和论文

### 作者微信：grapro666 QQ：3642795578 (支持部署调试、支持代做毕设)

### 接javaweb、python、小程序、H5、APP、各种管理系统、单片机、嵌入式等开发

### 选题+开题报告+任务书+程序定制+安装调试+论文+答辩ppt

**博客地址：
[https://blog.csdn.net/2303_76227485/article/details/154394410](https://blog.csdn.net/2303_76227485/article/details/154394410)**

**视频演示：
[https://www.bilibili.com/video/BV18u1kBjEJH/](https://www.bilibili.com/video/BV18u1kBjEJH/)**

**毕业设计所有选题地址：
[https://github.com/giteecode/allProject](https://github.com/giteecode/allProject)**

## 基于Java+Springboot+vue的外卖点餐系统(源代码+数据库+19000字论文+ppt)245
## 一、系统介绍
本项目前后端分离(可以改为ssm版本)，分为用户、商家、骑手、管理员四种角色。
### 1、用户：
- 注册、登录、菜品(浏览、收藏、加入购物车)、订单管理、支付、配送单查看、确认收货、收藏管理、评价、个人信息、密码修改
### 2、商家：
- 菜品管理、订单管理、配送单查看、评价管理
### 3、骑手：
- 订单查询、配送单管理
### 4、管理员：
- 数据大屏统计：订单总数、菜品总数、用户总数、订单状态分布柱状图、销售额折现图、菜品类型占比饼状图
- 用户管理、商家管理、商品分类管理、骑手管理、轮播图管理、新闻资讯管理、菜品管理、订单管理、配送单管理、评价管理
### 5、亮点：
将复杂的经营数据转化为直观的大屏可视化呈现，大幅提升了商家管理效率
## 二、所用技术
后端技术栈：
- Springboot
- mybatisPlus
- Mysql
- Maven

前端技术栈：
- Vue
- Vue-router
- axios
- elementui
- echarts

## 三、环境介绍
基础环境 :IDEA/eclipse, JDK1.8, Mysql5.7及以上, Maven3.6, node14, navicat

所有项目以及源代码本人均调试运行无问题 可支持远程调试运行

## 四、页面截图
文档截图
![](./picture/picture000.png)
![](./picture/picture00.png)
![](./picture/picture0.png)
### 1、用户：
![](./picture/picture1.png)
![](./picture/picture2.png)
![](./picture/picture3.png)
![](./picture/picture4.png)
![](./picture/picture5.png)
![](./picture/picture6.png)
![](./picture/picture7.png)
![](./picture/picture8.png)
![](./picture/picture9.png)
![](./picture/picture10.png)
![](./picture/picture11.png)
![](./picture/picture12.png)
![](./picture/picture13.png)
![](./picture/picture14.png)
![](./picture/picture15.png)
![](./picture/picture16.png)
![](./picture/picture17.png)
### 2、商家：
![](./picture/picture18.png)
![](./picture/picture19.png)
![](./picture/picture20.png)
![](./picture/picture21.png)
![](./picture/picture22.png)
### 3、骑手：
![](./picture/picture23.png)
![](./picture/picture24.png)
![](./picture/picture25.png)
![](./picture/picture26.png)
![](./picture/picture27.png)
### 4、管理员：
![](./picture/picture28.png)
![](./picture/picture29.png)
![](./picture/picture30.png)
![](./picture/picture31.png)
![](./picture/picture32.png)
![](./picture/picture33.png)
![](./picture/picture34.png)
![](./picture/picture35.png)
![](./picture/picture36.png)
![](./picture/picture37.png)
![](./picture/picture38.png)
## 五、浏览地址

前台地址：http://localhost:8080/orderFood/front/index.html

- 用户账号密码：user/123456

后台地址：http://localhost:8081

- 商家账号密码：shangjia/123456
- 骑手账号密码：qishou/123456
- 管理员账户密码：admin/123456


## 六、部署教程
1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并执行项目的sql文件

2. 使用IDEA/Eclipse导入orderFood项目，若为maven项目请选择maven，等待依赖下载完成

3. 修改application.yml里面的数据库配置,src/main/java/com/SpringbootSchemaApplication.java启动后端项目

4. vscode或idea打开orderFoodVue前端项目

5. 在编译器中打开terminal，执行npm install 依赖下载完成后执行 npm run serve,执行成功后会显示访问地址
