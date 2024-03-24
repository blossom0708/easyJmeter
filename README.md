<h1 align="center">EasyJmeter性能自动化测试平台</h1>

### 项目介绍
<p>
  <font>性能自动化测试平台依托于jmeter，在其上实现性能测试平台化管理。现在已实现了用例与测试数据管理、分布式压力测试、实时压测数据查看、测试结果查看与下载、历史测试数据查询和测试结果分析等功能。</font>
  <font>平台化的意义：1.方便团队协作和数据整合；2.定制化功能：在开源工具的基础上可以结合业务实现自定义功能，与外部系统对接，使得测试变得更加灵活和方便；3.降低成本：界面化比原有系统会操作相对简单，降低了压测环境的维护成本、操作成本和管理成本(重写或者二次开发)。</font>
  <font>平台技术栈为 vue + spring boot 前后端分离实现，数据库使用的是mysql、mongodb、influxdb，文件存储使用minio文件服务器。</font>
</p>


### 部分模块展示

![用例列表](https://img-blog.csdnimg.cn/direct/d4cde4d0325d4060bc6075c880db6295.jpeg#pic_center)

![编辑用例](https://img-blog.csdnimg.cn/direct/74b1642b8b134e30aa37b75766aa416d.jpeg#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/d5a17d1478434266ab211193b111b030.jpeg#pic_center)

![图表报告1](https://img-blog.csdnimg.cn/direct/d348bef4da924558b438df110ed5947a.jpeg#pic_center)

### 系统架构
![系统架构](https://img-blog.csdnimg.cn/direct/1c82503a78a644d484df87b6f9dd8f75.png#pic_center)
用户访问web页面，再由页面通过http请求访问业务后端服务。每台压力机服务器（只支持linux）上有且仅有一个jmeter，同时安装一个agent来控制jmeter运行，agent和服务端使用socketio实时通讯，用于服务端下发指令和agent上报状态。业务数据使用mysql进行存储，测试结果的详细数据使用mongodb存储，压测过程中的热数据使用influxdb存储，测试用例脚本、压测数据文件和压测结果文件（日志、jtl、报告)使用minio文件服务器存储。


### 项目本地部署(Windows)
### 1、数据库准备
<font>安装mysql8.0.33，启动mysql localhost:3306，新建数据库easy_jmeter，在application-dev.yml修改对应的设置</font>

<font>安装mongodb4.2.25 启动mongo localhost:27017，新建数据库easyJmeter，创建数据库使用的用户，在application-dev.yml修改对应的设置</font>
``` shell
use easyJmeter # 若没有则默认新建数据库
db.createUser({user:"blossom",pwd:"000618",roles:["readWrite"]})
```
<font>安装influxdb1.8，安装方式先启动influxd.exe 再在cmd中使用influx启动，地址 localhost:8086，新建数据库easyjmeter，在application-dev.yml修改对应的设置。</font>
``` shell
influx -username admin -password '123456'
```
<font>安装minio文件服务器 用命令行启动，界面有密码及用户名，在application-dev.yml修改对应的设置。 </font>
``` shell
.\minio.exe server D:\minio\data --console-address "127.0.0.1:9004" --address "127.0.0.1:9005"
```

### 3、前端部署（使用Vscode）

  1. 切换到web目录下，/web为前端服务，前端需下载vue，node版本v12.13.0（也可以设置node多版本切换），下载依赖npm install，注：windows下下载依赖时，检查package.json 文件中是不是有fsevents相关依赖，如果报错则删除fsevents相关依赖。
  2. 运行命令 npm run serve，出现运行地址则可以访问前端页面，前端启动成功。![前端页面启动成功](https://cdn.nlark.com/yuque/0/2024/png/42935774/1711269846624-d99aa21d-09a0-491a-8134-9f5446e98616.png?x-oss-process=image%2Fformat%2Cwebp)
  3. 部署则运行 npm run build

### 后端部署(使用idea)
  1. idea连接mysql数据库，新建mysql数据库easy_jmeter，在easy_jmeter中初始化/data/schema.sql。
  2. 在/api/pom.xml中右键使用maven导入配置，导入成功则在上方Build中重新构建项目
  3. 运行 EasyJmeterApplication.java

