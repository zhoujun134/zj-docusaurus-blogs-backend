package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.ZsFileManager;
import com.zj.zs.dao.mapper.ZsFileMapper;
import com.zj.zs.domain.entity.ZsFileDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ImageMapperImpl
 * @Author zj
 * @Description
 * @Date 2024/4/9 23:25
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsFileManagerImpl extends ServiceImpl<ZsFileMapper, ZsFileDO> implements ZsFileManager {

    @Override
    public List<ZsFileDO> randomOne(int pageSize) {
        // 总记录数
        int count = count();
        // 随机数起始位置
        int randomCount = (int) (Math.random() * count);
        // 保证能展示10个数据
        if (randomCount > count - pageSize) {
            randomCount = count - pageSize;
        }
        return lambdaQuery()
                .orderByDesc(ZsFileDO::getId)
                .last("limit " + randomCount + ", 10")
                .list();
    }
}
