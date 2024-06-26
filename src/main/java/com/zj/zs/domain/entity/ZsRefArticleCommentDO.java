package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName ZsRefArticleCommentDO
 * @Author zj
 * @Description
 * @Date 2024/4/20 21:56
 * @Version v1.0
 **/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("zs_ref_article_comment")
public class ZsRefArticleCommentDO  extends BaseDO {


    @TableId(type = IdType.AUTO)
    private Long id;
    private String articleId;

    private String commentId;

    /**
     * 评论级别
     */
    private String commentLevel;
}
