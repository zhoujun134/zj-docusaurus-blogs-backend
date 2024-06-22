package com.zj.zs.controller;

import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.article.ArchivistDto;
import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CommentDto;
import com.zj.zs.domain.dto.page.ZsPageDto;
import com.zj.zs.domain.dto.request.ArticleDetailReqDto;
import com.zj.zs.domain.dto.request.ArticleReqDto;
import com.zj.zs.domain.dto.request.CommentLikeSubmitReqDto;
import com.zj.zs.domain.dto.request.CommentSubmitReqDto;
import com.zj.zs.service.ArticleService;
import com.zj.zs.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName ZsArticleController
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:43
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/article")
@AllArgsConstructor
@Slf4j
public class ZsArticleController {

    private ArticleService articleService;

    @PostMapping("/detail")
    public Result<ArticleDto> detail(@RequestBody @Valid ArticleDetailReqDto request) {
        log.info("ZsArticleController######list: request={}", JsonUtils.toString(request));
        ArticleDto result = articleService.detail(request);
        return Result.ok(result);
    }
    @GetMapping("/archivist")
    public Result<List<ArchivistDto>> archivist() {
        List<ArchivistDto> archivist = articleService.archivist();
        return Result.ok(archivist);
    }

    @PostMapping("/list")
    public Result<ZsPageDto<ArticleDto>> list(@RequestBody @Valid ArticleReqDto request) {
        log.info("ZsArticleController######list: request={}", JsonUtils.toString(request));
        ZsPageDto<ArticleDto> result = articleService.page(request);
        return Result.ok(result);
    }

    @GetMapping("/comment")
    public Result<List<CommentDto>> getComment(@RequestParam("articleId") String articleId) {
        log.info("ZsArticleController######getComment: request={}", articleId);
        return Result.ok(articleService.getCommentList(articleId));
    }

    @PostMapping("/comment/submit")
    public Result<Boolean> submitComment(@RequestBody @Valid CommentSubmitReqDto request) {
        log.info("ZsArticleController######submitComment: request={}", JsonUtils.toString(request));
        if (StringUtils.isBlank(request.getArticleId()) && StringUtils.isBlank(request.getParentCommentId())) {
            return Result.fail("文章id，父评论id不能同时为空");
        }
        return Result.ok(articleService.submitComment(request));
    }

    @PostMapping("/comment/like")
    public Result<Boolean> submitCommentLike(@RequestBody @Valid CommentLikeSubmitReqDto request) {
        log.info("ZsArticleController######submitCommentLike: request={}", JsonUtils.toString(request));
        return Result.ok(articleService.submitComment(request));
    }

    @GetMapping("/generateMdFile")
    public Result<Boolean> generateMdFile(@RequestParam("parentPath") String parentPath) {
        articleService.generateMdFile(parentPath);
        return Result.ok(true);
    }
}
