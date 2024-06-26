package com.zj.zs.domain.dto.request.admin;

import com.google.common.collect.Lists;
import com.zj.zs.constants.DocusaurusFileTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName AddArticleReqDto
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:52
 * @Version v1.0
 **/
@Data
public class AddArticleReqDto {

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空！")
    private String title;

    /**
     * 文章摘要
     */
    private String articleAbstract;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空！")
    private String content;

    /**
     * 文章首页图片
     */
    private String headerImageUrl;


    /**
     * 文章类别 id 列表
     */
    private List<String> categoryIdList = Lists.newArrayList();

    /**
     * 文章标签 id 列表
     */
    private List<String> tagIdList = Lists.newArrayList();

    private DocusaurusFileTypeEnum fileType =  DocusaurusFileTypeEnum.BLOG;

    private String filePath =  "";
}
