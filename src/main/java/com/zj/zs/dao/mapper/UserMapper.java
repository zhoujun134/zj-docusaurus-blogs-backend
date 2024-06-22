package com.zj.zs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.zs.domain.entity.ZsUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<ZsUserDO> {
}
