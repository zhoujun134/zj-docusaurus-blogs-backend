package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName ZsRefArticleCategoryDO
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:45
 * @Version v1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("zs_ref_article_category")
public class ZsRefArticleCategoryDO extends BaseDO {

    private String articleId;

    private String categoryId;
}
