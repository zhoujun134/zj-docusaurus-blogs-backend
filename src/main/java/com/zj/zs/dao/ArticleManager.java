package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.dto.article.ArchivistDto;
import com.zj.zs.domain.dto.request.ArticleReqDto;
import com.zj.zs.domain.entity.ZsArticleDO;

import java.util.List;

/**
 * @ClassName ArticleManager
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:22
 * @Version v1.0
 **/
public interface ArticleManager extends IService<ZsArticleDO> {

    Page<ZsArticleDO> pageList(ArticleReqDto request);

    ZsArticleDO getByArticleId(String articleId);

    List<ArchivistDto> archivist();
}
