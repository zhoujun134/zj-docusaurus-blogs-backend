package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName ZsRefArticleTagDO
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:49
 * @Version v1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("zs_ref_article_tag")
public class ZsRefArticleTagDO extends BaseDBDO {

    private String articleId;

    private String tagId;
}
