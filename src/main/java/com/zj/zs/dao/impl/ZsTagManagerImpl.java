package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zj.zs.dao.ZsRefArticleTagManager;
import com.zj.zs.dao.ZsTagManager;
import com.zj.zs.dao.mapper.ZsTagMapper;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.domain.entity.ZsRefArticleTagDO;
import com.zj.zs.domain.entity.ZsTagDO;
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
 * @ClassName ZsTagManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:23
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsTagManagerImpl extends ServiceImpl<ZsTagMapper, ZsTagDO> implements ZsTagManager {

    @Resource
    private ZsRefArticleTagManager zsRefArticleTagManager;

    @Override
    public List<ZsTagDO> list(ZsSearchReqDto request) {
        if (Objects.isNull(request) || StringUtils.isBlank(request.getSearchKey())) {
            return this.list();
        }
        return lambdaQuery()
                .eq(ZsTagDO::getName, request.getSearchKey())
                .list();
    }

    @Override
    public boolean createRefInfo(String articleId, List<String> tagIdList) {
        if (StringUtils.isBlank(articleId) || CollectionUtils.isEmpty(tagIdList)) {
            log.warn("ZsTagManagerImpl##createRefInfo is blank articleId={}, tagIdList={}",
                    articleId, JsonUtils.toString(tagIdList));
            return false;
        }
        List<ZsRefArticleTagDO> refList = Safes.of(tagIdList).stream()
                .filter(StringUtils::isNotBlank)
                .map(tagId -> new ZsRefArticleTagDO()
                        .setTagId(tagId)
                        .setArticleId(articleId))
                .toList();
        if (CollectionUtils.isEmpty(refList)) {
            log.warn("ZsTagManagerImpl##createRef build is blank articleId={}, tagIdList={}",
                    articleId, JsonUtils.toString(refList));
            return false;
        }
        return zsRefArticleTagManager.saveBatch(refList);
    }

    /**
     * 这里可以使用一个 sql 连表查询出对应的结果
     * @param articleIdList 文章id列表
     * @return 文章id与标签列表的映射
     */
    @Override
    public Map<String, List<ZsTagDO>> getByArticleIdList(List<String> articleIdList) {
        if (CollectionUtils.isEmpty(articleIdList)) {
            return Collections.emptyMap();
        }
        List<ZsRefArticleTagDO> refTagList = zsRefArticleTagManager.getByArticleIdList(articleIdList);
        if (CollectionUtils.isEmpty(refTagList)) {
            return Collections.emptyMap();
        }
        Set<String> tagIdSet = Sets.newHashSet();
        Map<String, List<ZsRefArticleTagDO>> refArticleGroupMap = Safes.of(refTagList).stream()
                .peek(refTag -> tagIdSet.add(refTag.getTagId()))
                .collect(Collectors.groupingBy(ZsRefArticleTagDO::getArticleId));
        List<ZsTagDO> tagList = lambdaQuery().in(ZsTagDO::getTagId, tagIdSet)
                .list();
        if (CollectionUtils.isEmpty(tagList)) {
            return Collections.emptyMap();
        }
        Map<String, ZsTagDO> tagMap = Safes.of(tagList).stream()
                .collect(Collectors.toMap(ZsTagDO::getTagId, Function.identity(), (x, y) ->  x));
        Map<String, List<ZsTagDO>> result = Maps.newHashMap();
        Safes.of(refArticleGroupMap).forEach((articleId, refList) -> {
            List<ZsTagDO> articleTagList =  Safes.of(refList).stream()
                    .map(refArticle -> tagMap.get(refArticle.getTagId()))
                    .filter(Objects::nonNull)
                    .toList();
            result.put(articleId, articleTagList);
        });
        return result;
    }

    @Override
    public List<ZsTagDO> listByNames(List<String> tagNames) {
        if (CollectionUtils.isEmpty(tagNames)) {
            return Collections.emptyList();
        }
        return lambdaQuery().in(ZsTagDO::getName, tagNames)
                .list();
    }
    @Override
    public List<ZsTagDO> getByArticleId(String articleId) {
        List<ZsRefArticleTagDO> refList =
                zsRefArticleTagManager.getByArticleIdList(Collections.singletonList(articleId));
        List<String> tagIdList = Safes.of(refList).stream()
                .map(ZsRefArticleTagDO::getTagId)
                .distinct()
                .toList();
        if (CollectionUtils.isEmpty(tagIdList)) {
            return Collections.emptyList();
        }

        return lambdaQuery()
                .eq(ZsTagDO::getTagId, tagIdList)
                .list();
    }
}
