<h1 align="center">EasyJmeter性能自动化测试平台</h1>

### 项目介绍
<p>
  <font>性能自动化测试平台依托于jmeter，在其上实现性能测试平台化管理。现在已实现了用例与测试数据管理、分布式压力测试、实时压测数据查看、测试结果查看与下载、历史测试数据查询和测试结果分析等功能。</font>
  <font>平台技术栈为 vue + spring boot 前后端分离实现，数据库使用的是mysql、mongodb、influxdb，文件存储使用minio文件服务器。</font>
</p>

### 数据库准备
<font>安装mysql8.0.33，启动mysql localhost:3306，新建数据库easy_jmeter</font>

<font>安装mongodb4.2.25，默认用户名admin密码admin，新建数据库easyJmeter</font>

<font>安装influxdb1.8，安装方式先启动influxd.exe 再使用influx -username admin -password '123456'，新建数据库easyjmeter</font>

<font>安装minio文件服务器 用命令行启动，界面有密码及用户名。 .\minio.exe server D:\minio\data --console-address "127.0.0.1:9004" --address "127.0.0.1:9005"</font>

### 部分模块展示

![用例列表](https://img-blog.csdnimg.cn/direct/d4cde4d0325d4060bc6075c880db6295.jpeg#pic_center)

![编辑用例](https://img-blog.csdnimg.cn/direct/74b1642b8b134e30aa37b75766aa416d.jpeg#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/d5a17d1478434266ab211193b111b030.jpeg#pic_center)

![图表报告1](https://img-blog.csdnimg.cn/direct/d348bef4da924558b438df110ed5947a.jpeg#pic_center)

### 系统架构
![系统架构](https://img-blog.csdnimg.cn/direct/1c82503a78a644d484df87b6f9dd8f75.png#pic_center)
用户访问web页面，再由页面通过http请求访问业务后端服务。每台压力机服务器（只支持linux）上有且仅有一个jmeter，同时安装一个agent来控制jmeter运行，agent和服务端使用socketio实时通讯，用于服务端下发指令和agent上报状态。业务数据使用mysql进行存储，测试结果的详细数据使用mongodb存储，压测过程中的热数据使用influxdb存储，测试用例脚本、压测数据文件和压测结果文件（日志、jtl、报告)使用minio文件服务器存储。


### 项目本地调试
**web（前端）**

   在 web 目录下执行

``` javascript
npm install
npm run serve
```
**api（后端）**

   在 api 目录下启动EasyJmeterApplication
   server端启动修改配置文件

``` shell
socket.server.enable=true
```
​	agent端启动修改配置文件
``` shell
socket.client.enable=true
```

### 普通部署

  1. 安装mysql5.7数据库
  2. 安装mongodb4.2数据库
  3. 安装influxdb1.8数据库
  4. 安装minio文件服务器，设置指定bucket的Access Policy为public
  5. 部署server、agent。代码结构中api目录下为后端目录，后端使用springboot，修改配置文件并打包。其中作为server启动时设置socket.server.enable为true，作为agent启动时设置socket.client.enable为true，agent需要设置服务端socketio地址serverUrl。agent所在压力机需要配置jmete安装路径作为环境变量JMETER_HOME.
  6. 前端服务打包。代码结构中web目录下为前端服务，前端使用vue，node版本v12.13.0，打包命令 npm run build。
  7. 安装nginx。将web目录下default.conf按照实际情况修改，将前端包和配置文件放入nginx指定目录下启动。

### 容器化部署

 1. 构建后端jar包，代码结构中api目录下为后端目录，后端框架springboot,maven构建命令，`mvn clean package`。
 2. 构建前端dist包，前端使用vue，node版本v12.13.0，打包命令 `npm run build`。
 3. 编辑项目根目录下docker-compose.yaml 文件，修改environment中的minio地址和influxdb地址为实际地址，修改volumes中宿主机jmeter地址为实际地址。项目根目录下执行`docker-compose up -d`构建镜像并启动。


