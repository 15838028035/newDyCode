# 项目简介
内容主要包含：

| 微服务角色                 | 对应的技术选型                              |
| --------------------- | ------------------------------------ |
| 注册中心(Register Server) | Eureka                               |
| 服务提供者                 | spring mvc、mybatis 、mysql 等       |
| API Gateway           | Zuul                                 |


# 准备

## 环境准备：

| 工具    | 版本或描述                |
| ----- | -------------------- |
| JDK   | 1.8                  |
| IDE   | STS 或者 IntelliJ IDEA |
| Maven | 3.x                  |


## 主机规划：

| 项目名称                                     | 端口   | 描述                     | URL             |
| ---------------------------------------- | ---- | ---------------------- | --------------- |
| microservice-api-gateway                 | 8050 | API Gateway            | 详见文章                             |
| microservice-front-app                   | 8022 | 网站前台静态页面                                 |                 |
| microservice-discovery-eureka            | 9003 | 注册中心                                                |                 |
| weixin-java-open-demo                    | 8001 | 服务提供者                                            |微信公众账号接口相关服务   |
| microservice-provider-secrity            | 8002 | 服务提供者                                            | 用户、角色、权限、日志等增删改差服务      |

## 快速上手

     启  动microservice-discovery-eureka,
     microservice-provider-secrity,
     microservice-front-app,
     microservice-api-gateway
     
    访问登录首页  http://localhost:8022/login.html
     验证是否启动成功
     
  

     
