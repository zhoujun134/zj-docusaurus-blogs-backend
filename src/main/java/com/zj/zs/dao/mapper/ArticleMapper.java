package com.zj.zs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.zs.domain.entity.ZsArticleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ArticleMapper
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:23
 * @Version v1.0
 **/
@Mapper
public interface ArticleMapper  extends BaseMapper<ZsArticleDO> {
}
