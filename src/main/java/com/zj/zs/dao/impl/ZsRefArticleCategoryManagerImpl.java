package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.ZsRefArticleCategoryManager;
import com.zj.zs.dao.mapper.ZsRefArticleCategoryMapper;
import com.zj.zs.domain.entity.ZsRefArticleCategoryDO;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ZsRefArticleCategoryManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 23:23
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsRefArticleCategoryManagerImpl extends ServiceImpl<ZsRefArticleCategoryMapper, ZsRefArticleCategoryDO> implements ZsRefArticleCategoryManager {
    @Override
    public List<String> getArticleIdList(String categoryId) {
        if (StringUtils.isBlank(categoryId)) {
            return Collections.emptyList();
        }
        return Safes.of(lambdaQuery()
                        .select(ZsRefArticleCategoryDO::getArticleId)
                        .eq(ZsRefArticleCategoryDO::getCategoryId, categoryId)
                        .list())
                .stream()
                .map(ZsRefArticleCategoryDO::getArticleId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<ZsRefArticleCategoryDO> getByArticleIdList(List<String> articleIdList) {
        if (CollectionUtils.isEmpty(articleIdList)) {
            return Collections.emptyList();
        }
        return lambdaQuery()
                .in(ZsRefArticleCategoryDO::getArticleId, articleIdList)
                .list();
    }
}
