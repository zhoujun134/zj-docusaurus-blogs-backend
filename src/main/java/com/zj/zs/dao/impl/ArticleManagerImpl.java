package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.zj.zs.dao.ArticleManager;
import com.zj.zs.dao.ZsRefArticleCategoryManager;
import com.zj.zs.dao.ZsRefArticleTagManager;
import com.zj.zs.dao.mapper.ArticleMapper;
import com.zj.zs.domain.dto.article.ArchivistDto;
import com.zj.zs.domain.dto.request.ArticleReqDto;
import com.zj.zs.domain.entity.ZsArticleDO;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @ClassName ArticleManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:23
 * @Version v1.0
 **/
@Slf4j
@Component
public class ArticleManagerImpl extends ServiceImpl<ArticleMapper, ZsArticleDO> implements ArticleManager {

    @Resource
    private ZsRefArticleTagManager zsRefArticleTagManager;
    @Resource
    private ZsRefArticleCategoryManager zsRefArticleCategoryManager;

    @Override
    public Page<ZsArticleDO> pageList(ArticleReqDto request) {
        List<String> categoryArticleIdList = zsRefArticleCategoryManager.getArticleIdList(request.getCategoryId());
        List<String> tagArticleIdList = zsRefArticleTagManager.getArticleIdList(request.getTagId());
        Set<String> articleIdSet = Sets.newHashSet();
        if (StringUtils.isNotBlank(request.getCategoryId()) && CollectionUtils.isEmpty(categoryArticleIdList)) {
            return new Page<>(request.getPageNumber(), request.getPageSize());
        }
        articleIdSet.addAll(categoryArticleIdList);
        if (StringUtils.isNotBlank(request.getTagId()) && CollectionUtils.isEmpty(tagArticleIdList)) {
            return new Page<>(request.getPageNumber(), request.getPageSize());
        }
        articleIdSet.addAll(tagArticleIdList);
        LambdaQueryWrapper<ZsArticleDO> queryWrapper = new LambdaQueryWrapper<>();
        String keyword = request.getKeyword();
        queryWrapper.in(CollectionUtils.isNotEmpty(articleIdSet), ZsArticleDO::getArticleId, articleIdSet)
                .or(StringUtils.isNotBlank(keyword), wrapper ->
                        wrapper.like(ZsArticleDO::getContent, keyword)
                                .or()
                                .like(ZsArticleDO::getTitle, keyword)
                                .or()
                                .like(ZsArticleDO::getArticleAbstract, keyword))
                .orderByDesc(ZsArticleDO::getCreateTime);
        Page<ZsArticleDO> page = new Page<>(request.getPageNumber(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public ZsArticleDO getByArticleId(String articleId) {
        if (StringUtils.isBlank(articleId)) {
            return null;
        }
        List<ZsArticleDO> result = lambdaQuery()
                .eq(ZsArticleDO::getArticleId, articleId)
                .list();
        return Safes.first(result);
    }

    @Override
    public List<ArchivistDto> archivist() {
        StopWatch stopWatch = new StopWatch("archivist 统计时间");
        stopWatch.start("数据库查询");
        List<ZsArticleDO> allArticles = lambdaQuery()
                .select(ZsArticleDO::getIsDelete, ZsArticleDO::getTitle,
                        ZsArticleDO::getCreateTime, ZsArticleDO::getArticleId, ZsArticleDO::getId)
                .orderByDesc(ZsArticleDO::getCreateTime)
                .last("limit 20")
                .list();
        stopWatch.stop();
        stopWatch.start("转换和排序处理");
        List<ArchivistDto> result = Safes.of(allArticles).stream()
                .map(articleDO -> new ArchivistDto(articleDO.getArticleId(),
                        articleDO.getTitle(), articleDO.getCreateTime(),
                        JsonUtils.format.format(articleDO.getCreateTime())))
                .sorted((o1, o2) -> {
                    if (o1.getCreateTime().isBefore(o2.getCreateTime())) {
                        return 1;
                    }
                    if (o1.getCreateTime().isAfter(o2.getCreateTime())) {
                        return -1;
                    }
                    return 0;
                }).toList();
        stopWatch.stop();
        log.info("##archivist:优化访问接口响应: {}", stopWatch.prettyPrint());
        return result;
    }
}
