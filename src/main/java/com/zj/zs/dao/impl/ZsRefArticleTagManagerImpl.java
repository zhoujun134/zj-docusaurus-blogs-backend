package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.ZsRefArticleTagManager;
import com.zj.zs.dao.mapper.ZsRefArticleTagMapper;
import com.zj.zs.domain.entity.ZsRefArticleTagDO;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ZsRefArticleTagManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 23:35
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsRefArticleTagManagerImpl extends ServiceImpl<ZsRefArticleTagMapper, ZsRefArticleTagDO> implements ZsRefArticleTagManager {

    @Override
    public List<String> getArticleIdList(String tagId) {
        if (StringUtils.isBlank(tagId)) {
            return Collections.emptyList();
        }
        return Safes.of(lambdaQuery()
                        .select(ZsRefArticleTagDO::getArticleId)
                        .eq(ZsRefArticleTagDO::getTagId, tagId)
                        .list())
                .stream()
                .map(ZsRefArticleTagDO::getArticleId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<ZsRefArticleTagDO> getByArticleIdList(List<String> articleIdList) {
        if (CollectionUtils.isEmpty(articleIdList)) {
            return Collections.emptyList();
        }
        return lambdaQuery()
                .in(ZsRefArticleTagDO::getArticleId, articleIdList)
                .list();
    }
}
