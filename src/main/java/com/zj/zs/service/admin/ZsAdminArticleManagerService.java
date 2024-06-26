package com.zj.zs.service.admin;

import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.docusaurus.DocusaurusPublishShellConfigDto;
import com.zj.zs.domain.dto.request.admin.AddArticleReqDto;

import java.util.List;

/**
 * @ClassName ZsArticleManagerService
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:57
 * @Version v1.0
 **/
public interface ZsAdminArticleManagerService {

    ArticleDto addArticle(AddArticleReqDto request);
    boolean addNewCategoryForNotExist(List<CategoryDto> categoryDto);
    boolean addTag(List<TagDto> tagDto);

    boolean setDocusaurusConfig(DocusaurusPublishShellConfigDto request);
}
