package com.zj.zs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.zs.domain.entity.monitor.AccessLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName AccessLogMapper
 * @Author zj
 * @Description
 * @Date 2024/4/25 23:05
 * @Version v1.0
 **/
@Mapper
public interface AccessLogMapper extends BaseMapper<AccessLogDO> {
}
