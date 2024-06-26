package com.zj.zs.service;

import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.request.admin.AddArticleReqDto;

/**
 * @ClassName DocusaurusService
 * @Author zj
 * @Description
 * @Date 2024/6/18 23:29
 * @Version v1.0
 **/
public interface DocusaurusService {

    void createDocusaurusMdFile(ArticleDto articleDTO, AddArticleReqDto request);
}
