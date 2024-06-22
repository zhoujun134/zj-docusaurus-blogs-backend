package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.monitor.AccessLogDO;

import java.util.List;

/**
 * @ClassName AccessLogManager
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:05
 * @Version v1.0
 **/
public interface AccessLogManager extends IService<AccessLogDO> {

    List<AccessLogDO> getBySessionIdInOneMinute(String ip);
}
