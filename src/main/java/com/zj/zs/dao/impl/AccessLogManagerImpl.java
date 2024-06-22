package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.AccessLogManager;
import com.zj.zs.dao.mapper.AccessLogMapper;
import com.zj.zs.domain.entity.monitor.AccessLogDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName AccessLogManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:06
 * @Version v1.0
 **/
@Slf4j
@Component
public class AccessLogManagerImpl extends ServiceImpl<AccessLogMapper, AccessLogDO> implements AccessLogManager {

    @Override
    public List<AccessLogDO> getBySessionIdInOneMinute(String ip) {
        if (StringUtils.isBlank(ip)) {
            return Collections.emptyList();
        }
        return lambdaQuery().eq(AccessLogDO::getSessionId, ip)
                .between(AccessLogDO::getCreateTime, LocalDateTime.now().minusMinutes(1), LocalDateTime.now())
                .list();
    }
}
