package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.DictionaryManager;
import com.zj.zs.dao.mapper.DictionaryMapper;
import com.zj.zs.domain.entity.ZsDictionaryDO;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    @Transactional(rollbackFor = {Exception.class})
    public boolean saveOrUpdateByKey(String key, String value) {
        if (StringUtils.isAnyBlank(key, value)) {
            log.warn("##saveOrUpdateByKey## key or value is blank！key={}, value={}", key, value);
            return false;
        }
        List<ZsDictionaryDO> resList = lambdaQuery()
                .eq(ZsDictionaryDO::getKey, key)
                .list();
        if (CollectionUtils.isEmpty(resList)) {
            ZsDictionaryDO entity = new ZsDictionaryDO(key, value);
            boolean saveRes = this.save(entity);
            log.info("##saveOrUpdateByKey## save one data, saveRes={}, entity={}", saveRes, JsonUtils.toString(entity));
            return saveRes;
        }
        Safes.of(resList).forEach(entity -> entity.setValue(value));
        return this.updateBatchById(resList);
    }
}
