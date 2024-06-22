package com.zj.zs.domain.entity.monitor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName AccessLogDO
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:04
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("zs_access_log")
public class AccessLogDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String sessionId;

    private String url;

    private String method;

    private String requestContext;

    private String userAgent;

    private String referer;

    private LocalDateTime createTime;
}
