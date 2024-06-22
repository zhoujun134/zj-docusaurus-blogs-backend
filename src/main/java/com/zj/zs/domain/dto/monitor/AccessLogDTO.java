package com.zj.zs.domain.dto.monitor;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @ClassName AccessLogDTO
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:01
 * @Version v1.0
 **/
@Data
@Accessors(chain = true)
public class AccessLogDTO {

    private String sessionId;

    private String url;

    private String method;

    private String requestContext;

    private String userAgent;

    private String referer;

    private LocalDateTime visitedTime;
}
