package com.zj.zs.domain.dto.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ArticleDTO
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:33
 * @Version v1.0
 **/
@Data
public class ArticleDto {

    /**
     * 文章 id
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String articleAbstract;
    /**
     * 文章内容
     */
    private String tocString = "";

    /**
     * 文章内容
     */
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    private String headerImageUrl = "";

    /**
     * 文章标签
     */
    private List<TagDto> tagList = Collections.emptyList();

    /**
     * 文章分类
     */
    private List<CategoryDto> categoryList = Collections.emptyList();
}
