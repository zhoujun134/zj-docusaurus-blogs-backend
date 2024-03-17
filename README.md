# 个人使用的 spring boot 脚手架

## 项目描述

该项目为开发的脚手架。其中主要包含基础的结构定义

项目 swagger 地址 http://localhost:8080/zs/swagger-ui/index.html

```b
.
├── README.mdcd
├── pom.xml                                                                                                   # maven 依赖文件
└── src
    ├── main
    │             ├── java
    │             │             └── com
    │             │                 └── zj
    │             │                     └── zs
    │             │                         ├── ZsBootApplication.java
    │             │                         ├── config
    │             │                         │             ├── SwaggerConfig.java                              # swagger 文档配置
    │             │                         │             ├── ZsWebConfiguration.java                         # web 的配置信息
    │             │                         │             ├── filter                                          # filter 存放的目录
    │             │                         │             │             └── LoginFilter.java
    │             │                         │             ├── interceptor                                     # 拦截器
    │             │                         │             │             └── GlobalExceptionHandler.java
    │             │                         │             └── sso                                             # sso 配置信息
    │             │                         │                 └── ZsWebMvcConfigurer.java
    │             │                         ├── constants                                                     # 变量信息
    │             │                         │             └── GlobalConstants.java
    │             │                         ├── controller                                                    # api 接口
    │             │                         │             ├── ZsIndexController.java
    │             │                         │             └── admin
    │             │                         │                 └── AdminController.java
    │             │                         ├── converter                                                     # 数据对象转化
    │             │                         │             └── UserConverter.java
    │             │                         ├── dao                                                           # 数据访问层
    │             │                         │             ├── UserManager.java
    │             │                         │             ├── impl
    │             │                         │             │             └── UserManagerImpl.java
    │             │                         │             └── mapper
    │             │                         │                 └── UserMapper.java
    │             │                         ├── domain                                                        # 业务域
    │             │                         │             ├── Result.java
    │             │                         │             ├── ZsRequestContext.java
    │             │                         │             ├── dto
    │             │                         │             │             ├── UserInfoDto.java
    │             │                         │             │             └── request
    │             │                         │             │                 ├── LoginUserReqDTO.java
    │             │                         │             │                 └── RegisterUserInfoReqDTO.java
    │             │                         │             └── entity
    │             │                         │                 └── UserDO.java
    │             │                         ├── service                                                       # 服务层
    │             │                         │             ├── UserService.java
    │             │                         │             └── impl
    │             │                         │                 └── UserServiceImpl.java
    │             │                         └── utils                                                         # 常用工具类
    │             │                             ├── JsonUtils.java
    │             │                             ├── Safes.java
    │             │                             └── exception
    │             │                                 └── BusinessException.java
    │             └── resources
    │                 ├── application.yml
    │                 ├── mappers
    │                 ├── static
    │                 └── templates
    └── test
        └── java
            └── com
                └── zj
                    └── ZsBootApplicationTests.java
```

## 版本说明

```
jdk: 17
spring-boot: 2.7.3
mysql-connector-java: 8.0.33
springfox-swagger2: 2.9.2
lombok: 1.18.30
mybatis-plus-boot-starter: 3.3.1
mybatis-spring: 2.1.2
sa-token-spring-boot-starter: 1.37.0
gson: 2.10.1
```





## 项目文件说明

### com.zj.zs.config.sso.ZsWebMvcConfigurer

该配置中主要涉及 sso 的配置，对应哪些接口需要进行 sso 拦截。sso支持。

### com.zj.zs.config.filter.LoginFilter

这个 filter 主要是将对应的登录信息获取出来放在 `com.zj.zs.domain.ZsRequestContext`中，方便后面在相关请求中获取对应的用户信息。

### com.zj.zs.utils.exception

拦截器: 主要是对全局的一些异常进行拦截和处理。
## 项目初始化
```bash
git init
git add .
git commit -m "first commit"
git branch -M main
# 添加远程仓库
git remote add origin git@github.com:zhoujun134/zjBoot.git
git push -u origin main
```
