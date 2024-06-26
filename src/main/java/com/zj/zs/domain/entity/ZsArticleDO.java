package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.zs.constants.DocusaurusFileTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ArticleDO
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:12
 * @Version v1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zs_article")
public class ZsArticleDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String articleId;

    @TableField(value = "abstract")
    private String articleAbstract;

    private String content;

    /**
     * 文章 path, example: /blog/2023/03/17/zs-boot-docusaurus-blogs.md
     */
    private String path = "";

    /**
     * 文章类型
     * @link com.zj.zs.constants.DocusaurusFileTypeEnum
     */
    private String type = DocusaurusFileTypeEnum.BLOG.getCode();

}
