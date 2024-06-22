package com.zj.zs.service.monitor;

import com.zj.zs.domain.dto.monitor.AccessLogDTO;

/**
 * @ClassName AcessLogService
 * @Author zj
 * @Description
 * @Date 2024/4/25 22:58
 * @Version v1.0
 **/
public interface AccessLogService {

    void saveAccessLog(AccessLogDTO accessLogDTO);
}
