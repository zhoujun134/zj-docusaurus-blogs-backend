package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.ZsRefArticleCommentDO;

import java.util.List;

/**
 * @ClassName ZsRefArticleCommentManager
 * @Author zj
 * @Description
 * @Date 2024/4/20 22:00
 * @Version v1.0
 **/
public interface ZsRefArticleCommentManager  extends IService<ZsRefArticleCommentDO> {

    List<ZsRefArticleCommentDO> getOneLevelByArticleId(String articleId);
}
