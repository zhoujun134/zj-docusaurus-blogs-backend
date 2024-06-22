package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.ZsRefArticleCategoryDO;

import java.util.List;

/**
 * @ClassName ZsRefArticleCategoryManager
 * @Author zj
 * @Description
 * @Date 2024/4/14 23:23
 * @Version v1.0
 **/
public interface ZsRefArticleCategoryManager extends IService<ZsRefArticleCategoryDO> {
    List<String> getArticleIdList(String categoryId);

    List<ZsRefArticleCategoryDO> getByArticleIdList(List<String> articleIdList);

}
