package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zj.zs.dao.ZsCategoryManager;
import com.zj.zs.dao.ZsRefArticleCategoryManager;
import com.zj.zs.dao.mapper.ZsCategoryMapper;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.domain.entity.ZsCategoryDO;
import com.zj.zs.domain.entity.ZsRefArticleCategoryDO;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName ZsCategoryManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 21:55
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsCategoryManagerImpl extends ServiceImpl<ZsCategoryMapper, ZsCategoryDO> implements ZsCategoryManager {

    @Resource
    private ZsRefArticleCategoryManager zsRefArticleCategoryManager;

    @Override
    public List<ZsCategoryDO> list(ZsSearchReqDto request) {
        if (Objects.isNull(request) || StringUtils.isBlank(request.getSearchKey())) {
            return this.list();
        }
        return lambdaQuery()
                .eq(ZsCategoryDO::getName, request.getSearchKey())
                .list();
    }

    @Override
    public boolean createRefInfo(String articleId, List<String> categoryIdList) {
        if (StringUtils.isBlank(articleId) || CollectionUtils.isEmpty(categoryIdList)) {
            log.warn("ZsCategoryManagerImpl##createRefInfo is blank articleId={}, categoryIdList={}",
                    articleId, JsonUtils.toString(categoryIdList));
            return false;
        }
        List<ZsRefArticleCategoryDO> refList = Safes.of(categoryIdList).stream()
                .filter(StringUtils::isNotBlank)
                .map(categoryId -> new ZsRefArticleCategoryDO()
                        .setCategoryId(categoryId)
                        .setArticleId(articleId))
                .toList();
        if (CollectionUtils.isEmpty(refList)) {
            log.warn("ZsCategoryManagerImpl##createRef build is blank articleId={}, categoryIdList={}",
                    articleId, JsonUtils.toString(categoryIdList));
            return false;
        }
        return zsRefArticleCategoryManager.saveBatch(refList);
    }

    @Override
    public List<ZsCategoryDO> listByNames(List<String> categoryNames) {
        if (CollectionUtils.isEmpty(categoryNames)) {
            return Collections.emptyList();
        }
        return lambdaQuery().in(ZsCategoryDO::getName, categoryNames)
                .list();
    }

    @Override
    public Map<String, List<ZsCategoryDO>> getByArticleIdList(List<String> articleIdList) {
        if (CollectionUtils.isEmpty(articleIdList)) {
            return Collections.emptyMap();
        }
        List<ZsRefArticleCategoryDO> refCategoryList = zsRefArticleCategoryManager.getByArticleIdList(articleIdList);
        if (CollectionUtils.isEmpty(refCategoryList)) {
            return Collections.emptyMap();
        }
        Set<String> categgoryIdSet = Sets.newHashSet();
        Map<String, List<ZsRefArticleCategoryDO>> refArticleGroupMap = Safes.of(refCategoryList).stream()
                .peek(refTag -> categgoryIdSet.add(refTag.getCategoryId()))
                .collect(Collectors.groupingBy(ZsRefArticleCategoryDO::getArticleId));
        List<ZsCategoryDO> categoryList = lambdaQuery().in(ZsCategoryDO::getCategoryId, categgoryIdSet)
                .list();
        if (CollectionUtils.isEmpty(categoryList)) {
            return Collections.emptyMap();
        }
        Map<String, ZsCategoryDO> categoryMap = Safes.of(categoryList).stream()
                .collect(Collectors.toMap(ZsCategoryDO::getCategoryId, Function.identity(), (x, y) -> x));
        Map<String, List<ZsCategoryDO>> result = Maps.newHashMap();
        Safes.of(refArticleGroupMap).forEach((articleId, refList) -> {
            List<ZsCategoryDO> articleTagList = Safes.of(refList).stream()
                    .map(refArticle -> categoryMap.get(refArticle.getCategoryId()))
                    .filter(Objects::nonNull)
                    .toList();
            result.put(articleId, articleTagList);
        });
        return result;

    }
    @Override
    public List<ZsCategoryDO> getByArticleId(String articleId) {
        List<ZsRefArticleCategoryDO> refList =
                zsRefArticleCategoryManager.getByArticleIdList(Collections.singletonList(articleId));
        List<String> categoryIdList = Safes.of(refList).stream()
                .map(ZsRefArticleCategoryDO::getCategoryId)
                .distinct()
                .toList();
        if (CollectionUtils.isEmpty(categoryIdList)) {
            return Collections.emptyList();
        }

        return lambdaQuery()
                .in(ZsCategoryDO::getCategoryId, categoryIdList)
                .list();
    }

}
