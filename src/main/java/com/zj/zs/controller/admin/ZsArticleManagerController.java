package com.zj.zs.controller.admin;

import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.docusaurus.DocusaurusPublishShellConfigDto;
import com.zj.zs.domain.dto.request.admin.AddArticleReqDto;
import com.zj.zs.service.admin.ZsAdminArticleManagerService;
import com.zj.zs.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @ClassName ZsArticleManagerController
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:06
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/admin/zs/article")
@AllArgsConstructor
@Slf4j
public class ZsArticleManagerController {

    private ZsAdminArticleManagerService zsAdminArticleManagerService;

    @PostMapping("/add")
    public Result<ArticleDto> add(@RequestBody @Valid AddArticleReqDto request) {
        log.info("ZsArticleManagerController######add: request={}", JsonUtils.toString(request));
        ArticleDto articleDto = zsAdminArticleManagerService.addArticle(request);
        return Result.ok(articleDto);
    }

    @PostMapping("/category/add")
    public Result<Boolean> categoryAdd(@RequestBody @Valid @NotEmpty(message = "添加的文章分类列表不能为空！") List<CategoryDto> request) {
        log.info("ZsArticleManagerController######categoryAdd: request={}", JsonUtils.toString(request));
        boolean result = zsAdminArticleManagerService.addNewCategoryForNotExist(request);
        return Result.ok(result);
    }

    @PostMapping("/tag/add")
    public Result<Boolean> tagAdd(@RequestBody @Valid @NotEmpty(message = "添加的文章标签列表不能为空！") List<TagDto> request) {
        log.info("ZsArticleManagerController######tagAdd: request={}", JsonUtils.toString(request));
        boolean result = zsAdminArticleManagerService.addTag(request);
        return Result.ok(result);
    }

    @PostMapping("/docusaurus/config/add")
    public Result<Boolean> docusaurusConfig(@RequestBody @Valid DocusaurusPublishShellConfigDto request) {
        log.info("ZsArticleManagerController######docusaurusConfig: request={}", JsonUtils.toString(request));
        boolean result = zsAdminArticleManagerService.setDocusaurusConfig(request);
        return Result.ok(result);
    }
}
