package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.DictionaryManager;
import com.zj.zs.dao.mapper.DictionaryMapper;
import com.zj.zs.domain.entity.ZsDictionaryDO;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName DictionaryManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/11 21:59
 * @Version v1.0
 **/
@Slf4j
@Component
public class DictionaryManagerImpl extends ServiceImpl<DictionaryMapper, ZsDictionaryDO> implements DictionaryManager {

    @Override
    public String getByKey(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        List<ZsDictionaryDO> result = lambdaQuery()
                .eq(ZsDictionaryDO::getKey, key)
                .list();

        return Safes.firstT(result)
                .map(ZsDictionaryDO::getValue)
                .orElse("");
    }

    @Override
    public boolean updateByKey(String key, String value) {
        if (StringUtils.isAnyBlank(key, value)) {
            log.warn("##updateByKey## key or value is blank！key={}, value={}", key, value);
            return false;
        }
        return lambdaUpdate()
                .set(ZsDictionaryDO::getValue, value)
                .eq(ZsDictionaryDO::getKey, key)
                .update();
    }
}
