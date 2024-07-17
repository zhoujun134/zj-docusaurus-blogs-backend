# zj-docusaurus-blogs 的后端服务

基于 [zjBoot](https://github.com/zhoujun134/zjBoot) 构建的服务

本服务主要服务于 [zj-docusaurus-blogs](https://github.com/zhoujun134/zj-docusaurus-blogs) 的页面渲染

提供友链的配置，信息获取，邮件发送等功能。

本后端服务，提供了如下服务: 
+ 友链配置
+ 友链信息获取
+ 评论提交
+ 评论列表
+ 站点监控
  + QQ 邮箱报警信息发送
  + 异常 IP 访问黑名单
  + 访问日志记录

## 友链配置表

友链信息存放于表 `zs_friends` 表中。如果需要新增友链，只需要添加即可，如果删除，需要自行进行删除数据，或者标记 status 字段为 1 即可。
新增友链 sql 示例: 
```sql
INSERT INTO zs_friends (id, category_code, title, site_url, logo_url, email, description, create_username,
                                create_time, update_username, update_time, deleted)
VALUES (8, 'TOOLS_SITE', 'Z 不殊', 'https://zbus.top/', 'https://zbus.top/logo.png', null,
        '快乐，简单，程序改变世界', 'zs-boot-default-user', CURRENT_TIME, 'zs-boot-default-user',
        CURRENT_TIME, 0);
```
## 一些配置信息
站点的配置信息存放于 `zs_dictionary` 表中，主要有以下几种配置
### QQ 邮箱配置
这里的 QQ 邮箱配置，主要用户接收和发送邮件，在站点出现一些异常情况时，也可用于接收新的评论消息，推送消息。
配置信息为 `zs_dictionary` 中 key 为 `zs-boot-qq-email-config` 的配置，其配置的 value 为 json 结构:
```json
{
    "userSenderEmail": "您发送QQ邮件的邮箱",
    "userName": "您QQ 邮箱的用户名",
    "userPassword": "用户名对应的密码（授权吗）"
}
```

### 访问控制

这部分内容主要是，拦截一些异常的请求，拦截的 IP 存放于 `zs_dictionary` 表中，key 为 `zs.access.config.dict.key` 的配置，其配置的 value 为 json 结构:

```json
{
    "oneLevelBlackIpList": [
        "192.168.12.2"
    ],
    "blackIpConfigMap": {
        "192.168.12.3": {
            "ip": "192.168.12.3",
            "userAgents": [
                ""
            ],
            "urls": [
                ""
            ],
            "refererList": [
                ""
            ]
        }
    }
}
```
`oneLevelBlackIpList` 为一级拦截目标，只要在 `oneLevelBlackIpList` 中，就会被拦截。
`blackIpConfigMap` 为二级拦截目标，如果 `oneLevelBlackIpList` 中存在，则会基于 `userAgents`、`urls`、`refererList` 进行拦截，如果满足 `userAgents`、`urls`、`refererList` 的内容，也会进行拦截， 。

在使用之前，请确保访问时，请求的 header 中，必须携带 `User-Agent`, `Referer` 和 `x-real-ip`，其中 `x-real-ip` 为实际的客户端 ip 地址。

## 访问日志记录
访问的日志记录在 `zs_access_log` 表中。
