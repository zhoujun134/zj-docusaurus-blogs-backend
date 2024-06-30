package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.ZsDictionaryDO;

/**
 * @ClassName DictionaryManager
 * @Author zj
 * @Description
 * @Date 2024/4/11 21:58
 * @Version v1.0
 **/
public interface DictionaryManager extends IService<ZsDictionaryDO> {

    String getByKey(String key);
    boolean saveOrUpdateByKey(String key, String value);

}
