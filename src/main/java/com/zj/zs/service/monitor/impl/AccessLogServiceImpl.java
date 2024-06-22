package com.zj.zs.service.monitor.impl;

import com.zj.zs.converter.AccessConverter;
import com.zj.zs.dao.AccessLogManager;
import com.zj.zs.domain.dto.monitor.AccessLogDTO;
import com.zj.zs.domain.entity.monitor.AccessLogDO;
import com.zj.zs.service.cache.CacheService;
import com.zj.zs.service.monitor.AccessLogService;
import com.zj.zs.utils.QQSendEmailService;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.zj.zs.constants.GlobalConstants.*;

/**
 * @ClassName AcessLogServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:07
 * @Version v1.0
 **/
@Slf4j
@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Resource
    private AccessConverter converter;
    @Resource
    private AccessLogManager accessLogManager;
    @Resource
    private CacheService cacheService;
    @Resource
    private QQSendEmailService qqSendEmailService;


    @Override
    public void saveAccessLog(AccessLogDTO accessLogDTO) {
        if (StringUtils.isBlank(accessLogDTO.getSessionId())) {
            return;
        }
        String sessionId = accessLogDTO.getSessionId();
        AccessLogDO accessLog = converter.toDO(accessLogDTO);
        accessLogManager.save(accessLog);
        String redisKey = String.format(ACCESS_LOG_REDIS_KEY_PREFIX, sessionId);

        Long visitedCount = cacheService.getCountOrSet(redisKey, ACCESS_LOG_REDIS_KEY_EXPIRE_MINUTE);
        if (visitedCount >= ACCESS_LOG_ONE_IP_VISITED_MAX_ONE_MINUTE) {
            List<AccessLogDO> oneMinuteAccessLogs = accessLogManager.getBySessionIdInOneMinute(sessionId);
            sendEmailToManager(sessionId, oneMinuteAccessLogs);
        }
    }

    @Async
    public void sendEmailToManager(String ip, List<AccessLogDO> oneMinuteAccessLogs) {
        if (CollectionUtils.isEmpty(oneMinuteAccessLogs)) {
            return;
        }
        String isSendEmailKey = String.format(ACCESS_LOG_REDIS_EMAIL_KEY_PREFIX, ip);
        if (Boolean.TRUE.equals(cacheService.exist(isSendEmailKey))) {
            return;
        }
        List<String> urls = Safes.of(oneMinuteAccessLogs).stream()
                .map(AccessLogDO::getUrl)
                .toList();
        String urlString = String.join(",\n", urls);
        String text = String.format("请注意监控，存在异常访问 IP，ip = %s, 在 1 分钟内访问了如下 path: %s", ip, urlString);
        String subject = "【出现异常访问 IP】";
        qqSendEmailService.sendEmail(subject, text, "1161386101@qq.com");
        cacheService.setExpire(isSendEmailKey, true, 60);
    }
}
