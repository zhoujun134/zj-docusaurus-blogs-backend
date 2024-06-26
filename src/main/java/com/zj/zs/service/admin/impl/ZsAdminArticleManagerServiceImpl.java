package com.zj.zs.service.admin.impl;

import com.google.common.collect.Lists;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.converter.ArticleConverter;
import com.zj.zs.dao.ArticleManager;
import com.zj.zs.dao.ZsCategoryManager;
import com.zj.zs.dao.ZsFileManager;
import com.zj.zs.dao.ZsTagManager;
import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.docusaurus.DocusaurusPublishShellConfigDto;
import com.zj.zs.domain.dto.docusaurus.ExecuteResult;
import com.zj.zs.domain.dto.request.ArticleDetailReqDto;
import com.zj.zs.domain.dto.request.admin.AddArticleReqDto;
import com.zj.zs.domain.entity.ZsArticleDO;
import com.zj.zs.domain.entity.ZsCategoryDO;
import com.zj.zs.domain.entity.ZsFileDO;
import com.zj.zs.domain.entity.ZsTagDO;
import com.zj.zs.service.ArticleService;
import com.zj.zs.service.DocusaurusService;
import com.zj.zs.service.admin.ZsAdminArticleManagerService;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.Safes;
import com.zj.zs.utils.SystemCommandExecutor;
import com.zj.zs.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zj.zs.constants.GlobalConstants.DEFAULT_IMAGE_CATEGORY;

/**
 * @ClassName ZsAdminArticleManagerServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:59
 * @Version v1.0
 **/
@Slf4j
@Service
public class ZsAdminArticleManagerServiceImpl implements ZsAdminArticleManagerService {

    @Resource
    private ArticleManager articleManager;
    @Resource
    private ZsFileManager zsFileManager;
    @Resource
    private ArticleConverter articleConverter;
    @Resource
    private ZsCategoryManager zsCategoryManager;
    @Resource
    private ZsTagManager zsTagManager;
    @Resource
    private ArticleService articleService;
    @Resource
    private DocusaurusService docusaurusService;

    @Value("${docusaurus.dir.path}")
    private String parentPath;
    @Value("${docusaurus.nginx.site.dir.path}")
    private String nginxSitePath;
    @Value("${docusaurus.nginx.site.history.dir.path}")
    private String buildHistoryPath;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDto addArticle(AddArticleReqDto request) {
        final ZsArticleDO zsArticleDO = articleConverter.toZsArticleDO(request);
        List<String> categoryIdList = request.getCategoryIdList();
        if (CollectionUtils.isEmpty(categoryIdList)) {
            categoryIdList = Lists.newArrayList(GlobalConstants.DEFAULT_CATEGORY_ID.getCategoryId());
        }
        // 文章类别
        String articleId = zsArticleDO.getArticleId();
        zsCategoryManager.createRefInfo(articleId, categoryIdList);
        // 文章标签
        zsTagManager.createRefInfo(articleId, request.getTagIdList());
        boolean saveRes = articleManager.save(zsArticleDO);
        log.info("##addArticle: articleId={} saveRes={}, zsArticleDO={}, request={}",
                articleId, saveRes, JsonUtils.toString(zsArticleDO), JsonUtils.toString(request));
        if (StringUtils.isNotBlank(request.getHeaderImageUrl())) {
            ZsFileDO zsFileDO = new ZsFileDO(request.getTitle(), articleId,
                    DEFAULT_IMAGE_CATEGORY, request.getHeaderImageUrl());
            zsFileManager.save(zsFileDO);
        }
        ArticleDto detail = articleService.detail(new ArticleDetailReqDto(articleId));
        deployArticleToNginx(request, detail);
        return detail;
    }

    private void deployArticleToNginx(AddArticleReqDto request, ArticleDto detail) {
        // 生成md文件
        docusaurusService.createDocusaurusMdFile(detail, request);
        // 发布文章
        DocusaurusPublishShellConfigDto config = new DocusaurusPublishShellConfigDto()
                .setDocusaurusProjectPath(parentPath)
                .setNginxSitePath(nginxSitePath)
                .setBuildHistoryPath(buildHistoryPath);
        List<String> commands = SystemCommandExecutor.initCommandsByConfig(config);
        ExecuteResult executeResult = SystemCommandExecutor.executeCommands(commands);
        if (Objects.nonNull(executeResult)) {
            log.info("=============================================== 文章发布日志 start ===============================================");
            Safes.of(executeResult.getInfoMessage()).forEach(log::info);
            Safes.of(executeResult.getErrorMessage()).forEach(log::warn);
            log.info("=============================================== 文章发布日志 end ===============================================");
        }
    }

    @Override
    public boolean addNewCategoryForNotExist(List<CategoryDto> categoryDto) {
        List<String> categoryNames = Safes.of(categoryDto).stream()
                .map(CategoryDto::getName)
                .filter(StringUtils::isNotBlank)
                .toList();
        List<ZsCategoryDO> zsCategoryList = zsCategoryManager.listByNames(categoryNames);
        Map<String, ZsCategoryDO> categoryMap =  Safes.of(zsCategoryList).stream()
                .collect(Collectors.toMap(ZsCategoryDO::getName, Function.identity(), (x, y) -> x));
        List<ZsCategoryDO> needAddCategoryList = Safes.of(categoryNames).stream()
                .filter(name -> StringUtils.isNotBlank(name) && !categoryMap.containsKey(name))
                .map(name -> new ZsCategoryDO(UUIDUtils.uuid(), name))
                .toList();
        return zsCategoryManager.saveBatch(needAddCategoryList);
    }

    @Override
    public boolean addTag(List<TagDto> tagDtoList) {
        List<String> tagNames = Safes.of(tagDtoList).stream()
                .map(TagDto::getTagName)
                .filter(StringUtils::isNotBlank)
                .toList();
        List<ZsTagDO> tagList = zsTagManager.listByNames(tagNames);
        Map<String, ZsTagDO> tagMap =  Safes.of(tagList).stream()
                .collect(Collectors.toMap(ZsTagDO::getName, Function.identity(), (x, y) -> x));
        List<ZsTagDO> needAddTagList = Safes.of(tagNames).stream()
                .filter(name -> StringUtils.isNotBlank(name) && !tagMap.containsKey(name))
                .map(name -> new ZsTagDO(UUIDUtils.uuid(), name))
                .toList();
        return zsTagManager.saveBatch(needAddTagList);
    }

    @Override
    public boolean setDocusaurusConfig(DocusaurusPublishShellConfigDto request) {
        log.info("##setDocusaurusConfig: 设置全局 docusaurus 发布脚本配置文件！");
        GlobalConstants.docusaurusPublishConfig = request;
        return true;
    }
}
