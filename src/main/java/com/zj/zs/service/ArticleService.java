package com.zj.zs.service;

import com.zj.zs.domain.dto.article.ArchivistDto;
import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CommentDto;
import com.zj.zs.domain.dto.page.ZsPageDto;
import com.zj.zs.domain.dto.request.ArticleDetailReqDto;
import com.zj.zs.domain.dto.request.ArticleReqDto;
import com.zj.zs.domain.dto.request.CommentLikeSubmitReqDto;
import com.zj.zs.domain.dto.request.CommentSubmitReqDto;

import java.util.List;

/**
 * @ClassName ArticleService
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:31
 * @Version v1.0
 **/
public interface ArticleService {

    ZsPageDto<ArticleDto> page(ArticleReqDto request);

    ArticleDto detail(ArticleDetailReqDto request);

    List<CommentDto> getCommentList(String articleId);

    Boolean submitComment(CommentSubmitReqDto request);

    Boolean submitComment(CommentLikeSubmitReqDto request);

    List<ArchivistDto> archivist();

    void generateMdFile(String parentPath);
}
