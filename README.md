# 项目简介
内容主要包含：

| 微服务角色                 | 对应的技术选型                              |
| --------------------- | ------------------------------------ |
| 注册中心(Register Server) | Eureka                               |
| 服务提供者                 | spring mvc、spring-data-jpa、h2等       |
| 服务消费者                 | Ribbon/Feign消费服务提供者的接口               |
| 熔断器                   | Hystrix，包括Hystrix Dashboard以及Turbine |
| 配置服务                  | Spring Cloud Config Server           |
| API Gateway           | Zuul                                 |


# 准备

## 环境准备：

| 工具    | 版本或描述                |
| ----- | -------------------- |
| JDK   | 1.8                  |
| IDE   | STS 或者 IntelliJ IDEA |
| Maven | 3.x                  |

## 主机名配置：

| 主机名配置（C:\Windows\System32\drivers\etc\hosts文件） |
| ---------------------------------------- |
| 127.0.0.1 discovery config-server gateway movie user feign ribbon |

## 主机规划：

| 项目名称                                     | 端口   | 描述                     | URL             |
| ---------------------------------------- | ---- | ---------------------- | --------------- |
| microservice-api-gateway                 | 8050 | API Gateway            | 详见文章            |
| microservice-config-client               | 8041 | 配置服务的客户端               | 详见文章            |
| microservice-config-server-eureka        | 9004 | 配置服务                   | 详见文章            |
| microservice-front-app | 8022 | Hystrix Dashboard Demo | /feign/1        |
| microservice-discovery-eureka            | 9003 | 注册中心                   | /               |
| microservice-hystrix-dashboard           | 8030 | hystrix监控              | /hystrix.stream |
| microservice-hystrix-turbine             | 8031 | turbine                | /turbine.stream |
| microservice-provider-weixin             | 8001 | 服务提供者                  | /1              |
| microservice-provider-secrity            | 8002 | 服务提供者                  | 用户、角色、权限、日志等管理            |



## 快速上手
    数据库脚本docs目录下ddwxmgcodes_db.sql,数据库名称ddwxmgcodes_db, 密码root,账号root
    
     启  动microservice-discovery-eureka,
     microservice-config-server-eureka
     microservice-provider-secrity,
     microservice-front-app,
     microservice-api-gateway
     
    
    访问http://localhost:8022/swagger-ui.html
     验证是否启动成功
     
    访问登录首页  http://localhost:8022/login.html
  
## 原型图
https://pro.modao.cc/app/0QStVOm8dCsJXmf15TlMsftcwkeaPwi

## 参考网站
http://huantai2.palmble.com/Admin/Login/index.html

admin03 , 123456

 测试网址
 http://weixin.xrtz.org:8022/
     
