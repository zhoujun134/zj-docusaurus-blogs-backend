package com.zj.zs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.converter.ArticleConverter;
import com.zj.zs.dao.*;
import com.zj.zs.domain.dto.article.*;
import com.zj.zs.domain.dto.page.ZsPageDto;
import com.zj.zs.domain.dto.request.ArticleDetailReqDto;
import com.zj.zs.domain.dto.request.ArticleReqDto;
import com.zj.zs.domain.dto.request.CommentLikeSubmitReqDto;
import com.zj.zs.domain.dto.request.CommentSubmitReqDto;
import com.zj.zs.domain.entity.*;
import com.zj.zs.service.ArticleService;
import com.zj.zs.service.DocusaurusService;
import com.zj.zs.utils.QQSendEmailService;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName ArticleServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:31
 * @Version v1.0
 **/
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleManager articleManager;
    @Resource
    private ZsFileManager zsFileManager;
    @Resource
    private ArticleConverter articleConverter;
    @Resource
    private ZsCommentManager zsCommentManager;
    @Resource
    private ZsTagManager zsTagManager;
    @Resource
    private ZsCategoryManager zsCategoryManager;
    @Resource
    private QQSendEmailService qqSendEmailService;
    @Resource
    private DocusaurusService docusaurusService;

    private final static String DEFAULT_URL = "https://img.zbus.top/zbus/blog202403150806600.png";

    @Override
    public ZsPageDto<ArticleDto> page(ArticleReqDto request) {
        final Page<ZsArticleDO> articleDOPage = articleManager.pageList(request);
        final List<String> articleIdList = Safes.of(articleDOPage.getRecords()).stream()
                .map(ZsArticleDO::getArticleId)
                .distinct()
                .toList();
        final Map<String, List<ZsTagDO>> articleIdTagListGroup = zsTagManager.getByArticleIdList(articleIdList);
        final Map<String, List<ZsCategoryDO>> articleIdCategoryListGroup = zsCategoryManager.getByArticleIdList(articleIdList);
        final List<ZsFileDO> imageList = zsFileManager.randomOne(5);
        final IPage<ArticleDto> resultPage =
                articleDOPage.convert(articleDO -> {
                    final ArticleDto articleDTO = articleConverter.toArticleDTO(articleDO);
                    final ZsFileDO zsFileDO = Safes.randOne(imageList);
                    articleDTO.setHeaderImageUrl(Objects.nonNull(zsFileDO) ? zsFileDO.getUrl() : DEFAULT_URL);
                    final List<ZsTagDO> tagDOList = articleIdTagListGroup.get(articleDTO.getArticleId());
                    articleDTO.setTagList(articleConverter.convertTagList(tagDOList));
                    List<ZsCategoryDO> categoryDOList = articleIdCategoryListGroup.get(articleDTO.getArticleId());
                    articleDTO.setCategoryList(articleConverter.convertCategpryList(categoryDOList));
                    return articleDTO;
                });
        log.info("pages: {}, size: {}", resultPage.getPages(), resultPage.getSize());
        return new ZsPageDto<>(resultPage.getCurrent(), resultPage.getTotal(), resultPage.getPages(), resultPage.getRecords());
    }

    @Override
    public ArticleDto detail(ArticleDetailReqDto request) {
        final ZsArticleDO resByDB = articleManager.getByArticleId(request.getArticleId());
        final String articleId = request.getArticleId();
        final List<ZsCategoryDO> categoryDOList = zsCategoryManager.getByArticleId(articleId);
        final List<ZsTagDO> tagDOList = zsTagManager.getByArticleId(articleId);
        final ArticleDto result = articleConverter.toArticleDTO(resByDB);
        final List<TagDto> tagList = articleConverter.convertTagList(tagDOList);
        final List<CategoryDto> categoryList = articleConverter.convertCategpryList(categoryDOList);
        result.setTagList(tagList);
        result.setCategoryList(categoryList);
        return result;
    }

    @Override
    public List<CommentDto> getCommentList(String articleId) {
        if (StringUtils.isBlank(articleId)) {
            return Collections.emptyList();
        }
        List<ZsCommentDO> commentList = zsCommentManager.getByArticleId(articleId);
        if (CollectionUtils.isEmpty(commentList)) {
            return Collections.emptyList();
        }
        return Safes.of(commentList).stream()
                .map(zsCommentDO -> {
                    CommentDto commentDto = articleConverter.toCommentDto(zsCommentDO);
                    if (CollectionUtils.isEmpty(zsCommentDO.getReplyList())) {
                        return commentDto;
                    }
                    List<CommentDto> children = Safes.of(zsCommentDO.getReplyList()).stream()
                            .map(articleConverter::toCommentDto)
                            .toList();
                    commentDto.setChildren(children);
                    return commentDto;
                }).toList();
    }

    @Override
    public Boolean submitComment(CommentSubmitReqDto request) {
        // 父 ID 不为空的情况
        if (!StringUtils.isAnyBlank(request.getParentCommentId())) {
            Boolean result = zsCommentManager.submitCommentByParentId(request);
            sendEmailToManager(request);
            return result;
        }
        // 父ID 和 回复 ID 都为空时
        if (StringUtils.isBlank(request.getParentCommentId())
                && StringUtils.isBlank(request.getReplyCommentId())) {
            Boolean result = zsCommentManager.saveOneLevelComment(request);
            sendEmailToManager(request);
            return result;
        }
        return Boolean.FALSE;
    }

    private void sendEmailToManager(CommentSubmitReqDto request) {
        if (Objects.isNull(GlobalConstants.emailConfigDto)) {
            return;
        }
        String subject = "Z 不殊站点的【新评论通知】";
        String text = """
                文章ID: ${eArticleId}
                文章地址:&nbsp;&nbsp;<a href="https://zbus.top/${eArticleId}">点击访问原文</a>
                评论作者: ${eAuthor}
                评论邮箱: ${eEmail}
                """;
        text = text.replace("${eArticleId}", request.getArticleId());
        text = text.replace("${eContent}", request.getContent());
        text = text.replace("${eAuthor}", request.getAuthor());
        text = text.replace("${eEmail}", request.getEmail());
        qqSendEmailService.sendEmail(subject, text, GlobalConstants.emailConfigDto.getUserSenderEmail());
    }

    @Override
    public Boolean submitComment(CommentLikeSubmitReqDto request) {
        return zsCommentManager.updateLikeNum(request.getCommentId());
    }

    @Override
    public List<ArchivistDto> archivist() {
        return articleManager.archivist();
    }

    @Override
    public void generateMdFile(String parentPath) {
        final ZsPageDto<ArticleDto> page = this.page(new ArticleReqDto(1, 1000));
        final List<ArticleDto> articleList = page.getRecords();
        articleList.forEach(articleDTO -> docusaurusService.createDocusaurusMdFile(articleDTO, null));
    }
}
