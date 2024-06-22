package com.zj.zs.service.impl;

import com.google.common.collect.Sets;
import com.zj.zs.constants.DocusaurusFileTypeEnum;
import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.article.DocusaurusTemplateDto;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.service.DocusaurusService;
import com.zj.zs.utils.DateUtils;
import com.zj.zs.utils.FileUtils;
import com.zj.zs.utils.Pinyin4jUtil;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.zj.zs.constants.GlobalConstants.ARTICLE_MD_CREATE_TAG_TITLE_TEMPLATE;

/**
 * @ClassName DocusaurusServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/6/18 23:29
 * @Version v1.0
 **/
@Slf4j
@Service
public class DocusaurusServiceImpl implements DocusaurusService {
    private final static Set<String> blogTagSet = Sets.newHashSet("博客", "杂谈", "随笔");

    @Value("${docusaurus.dir.path}")
    private String parentPath;
    @Override
    public void createDocusaurusMdFile(ArticleDto articleDTO, DocusaurusFileTypeEnum fileTypeEnum) {
        DocusaurusFileTypeEnum fileType = fileTypeEnum;
        if (Objects.isNull(fileTypeEnum)) {
            boolean isBlog = Safes.of(articleDTO.getCategoryList()).stream()
                    .map(CategoryDto::getName)
                    .anyMatch(blogTagSet::contains);
            fileType = DocusaurusFileTypeEnum.DOCS;
            if (isBlog) {
                fileType = DocusaurusFileTypeEnum.BLOG;
            }
        }
        switch (fileType) {
            case BLOG -> createDocusaurusBlogMdFile(articleDTO, parentPath + "/blog");
            case DOCS -> createDocusaurusDocsMdFile(articleDTO, parentPath + "/docs");
            default -> log.warn("createDocusaurusMdFile: 不支持的文件生成类型！fileType: {}", fileType);
        }
    }

    private DocusaurusTemplateDto generateTemplateFileString(ArticleDto articleDTO) {
        final String createTime = DateUtils.formatByTransverse(articleDTO.getCreateTime());
        final String pingYinTitleName = Pinyin4jUtil.getPingYin(articleDTO.getTitle());
        // 文件名
        final String fileName = String.format("%s-%s.md", createTime, pingYinTitleName);
        String tagTitleTemplate = ARTICLE_MD_CREATE_TAG_TITLE_TEMPLATE;
        tagTitleTemplate = tagTitleTemplate.replace("${tilePath}", pingYinTitleName);
        tagTitleTemplate = tagTitleTemplate.replace("${tile}", articleDTO.getTitle());
        tagTitleTemplate = tagTitleTemplate.replace("${authorName}", "zhoujun134");
        final List<CategoryDto> categoryList = articleDTO.getCategoryList();
        final List<TagDto> tagList = articleDTO.getTagList();
        final List<String> categoryStringList = Safes.of(categoryList).stream()
                .map(CategoryDto::getName)
                .toList();
        final List<String> tagStringList = Safes.of(tagList).stream()
                .map(TagDto::getTagName)
                .toList();
        final Set<String> tagTitleSet = Sets.newHashSet();
        tagTitleSet.addAll(categoryStringList);
        tagTitleSet.addAll(tagStringList);
        String tagTitleString = String.join(", ", tagTitleSet);
        if (StringUtils.isBlank(tagTitleString)) {
            tagTitleString = "随笔";
        }
        tagTitleTemplate = tagTitleTemplate.replace("${tagListString}", tagTitleString);
        tagTitleTemplate = tagTitleTemplate.replace("${blogImage}", articleDTO.getHeaderImageUrl());
        log.info("生成的模板标题为: {}", tagTitleTemplate);
        log.info("生成的 fileName 为: {}", fileName);
        log.info("生成的 path 为: {}", pingYinTitleName);
        return new DocusaurusTemplateDto(tagTitleTemplate, fileName, createTime, categoryList);
    }

    private void createDocusaurusDocsMdFile(ArticleDto articleDTO, String parentPath) {
        DocusaurusTemplateDto docusaurusTemplateDto = generateTemplateFileString(articleDTO);
        String fileName = docusaurusTemplateDto.getFileName();
        String tagTitleTemplate = docusaurusTemplateDto.getTemplateTagString();
        List<CategoryDto> categoryList = docusaurusTemplateDto.getCategoryList();
        String dirPath = parentPath;
        if (!CollectionUtils.isEmpty(categoryList)) {
            dirPath = "/" + categoryList.get(0).getName();
        }
        final String needGeneratePath = FileUtils.fileDirGenerate(dirPath);
        generateFileTemplate(articleDTO, fileName, tagTitleTemplate, needGeneratePath);
    }

    private void generateFileTemplate(ArticleDto articleDTO, String fileName, String tagTitleTemplate, String needGeneratePath) {
        String articleAbstract = articleDTO.getArticleAbstract();
        if (StringUtils.isBlank(articleAbstract)) {
            articleAbstract = "";
        }
        final String content = String.format("""
                %s\s
                 %s\s
                <!-- truncate --> \s
                 %s""", tagTitleTemplate, articleAbstract, articleDTO.getContent());

        final String filePath = String.format("%s/%s", needGeneratePath, fileName);
        FileUtils.writeToNewFile(content, filePath);
    }

    private void createDocusaurusBlogMdFile(ArticleDto articleDTO, String parentPath) {
        DocusaurusTemplateDto docusaurusTemplateDto = generateTemplateFileString(articleDTO);
        String fileName = docusaurusTemplateDto.getFileName();
        String tagTitleTemplate = docusaurusTemplateDto.getTemplateTagString();
        final int year = articleDTO.getCreateTime().getYear();
        final int month = articleDTO.getCreateTime().getMonth().getValue();
        final String dirPath = FileUtils.fileDirGenerate(parentPath, year, month);
        generateFileTemplate(articleDTO, fileName, tagTitleTemplate, dirPath);
    }
}
