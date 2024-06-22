package com.zj.zs.converter;

import com.zj.zs.domain.dto.monitor.AccessLogDTO;
import com.zj.zs.domain.entity.monitor.AccessLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @ClassName AccessConverter
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:09
 * @Version v1.0
 **/
@Mapper(componentModel="spring")
public interface AccessConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    AccessLogDO toDO(AccessLogDTO dto);

    @Mapping(target = "visitedTime", expression = "java(java.time.LocalDateTime.now())")
    AccessLogDTO toDTO(String sessionId, String url, String method, String requestContext, String userAgent, String referer);
}
