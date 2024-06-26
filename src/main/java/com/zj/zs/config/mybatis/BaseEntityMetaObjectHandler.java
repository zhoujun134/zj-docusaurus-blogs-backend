package com.zj.zs.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zj.zs.domain.ZsRequestContext;
import com.zj.zs.domain.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    public BaseEntityMetaObjectHandler() {
        log.info("init BaseEntityMetaObjectHandler");
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        String username = "";
        try {
            UserInfoDto curContext = ZsRequestContext.getCurContext();
            if (Objects.nonNull(curContext)) {
                username = curContext.getUsername();
            }
            this.strictInsertFill(metaObject, "createUsername", String.class, username);
            this.strictInsertFill(metaObject, "updateUsername", String.class, username);
        } catch (RuntimeException e) {
            log.error("修改数据库填充用户信息时出现异常", e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        String username = "";
        try {
            UserInfoDto curContext = ZsRequestContext.getCurContext();
            if (Objects.nonNull(curContext)) {
                username = curContext.getUsername();
            }
            this.strictInsertFill(metaObject, "updateUsername", String.class, username);
        } catch (RuntimeException e) {
            log.error("修改数据库填充用户信息时出现异常", e);
        }
    }
}
