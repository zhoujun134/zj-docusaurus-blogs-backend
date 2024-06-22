package com.zj.zs.service.task.impl;

import com.zj.zs.dao.ArticleManager;
import com.zj.zs.domain.dto.openai.OpenApiResDto;
import com.zj.zs.domain.entity.ZsArticleDO;
import com.zj.zs.service.OpenAiService;
import com.zj.zs.service.task.ZsTaskService;
import com.zj.zs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zj.zs.constants.GlobalConstants.ARTICLE_ABSTRACT_PROMOTE;

/**
 * @ClassName ZsTaskServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/27 13:15
 * @Version v1.0
 **/
@Slf4j
@Service
public class ZsTaskServiceImpl implements ZsTaskService {
    @Resource
    private ArticleManager articleManager;
    @Resource
    private OpenAiService openAiService;

    @Async
    public void syncArticleAbstract() {
        log.info("##syncArticleAbstract:开始同步文章摘要");
        List<ZsArticleDO> allArticles = articleManager.list();
        allArticles.forEach(article -> {
            String content = ARTICLE_ABSTRACT_PROMOTE + article.getContent();
            OpenApiResDto openApiResDto = openAiService.generateText(content);
            if (!StringUtils.equals("0", openApiResDto.getCode())) {
                log.warn("##syncArticleAbstract: openApiResDto={}, content={}", content, JsonUtils.toString(openApiResDto));
                return;
            }
            String resContent = openApiResDto.getResContent();
            article.setArticleAbstract(resContent);
            articleManager.updateById(article);
        });
    }
}
