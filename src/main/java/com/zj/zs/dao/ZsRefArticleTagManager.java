package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.ZsRefArticleTagDO;

import java.util.List;

/**
 * @ClassName ZsRefArticleTagManager
 * @Author zj
 * @Description
 * @Date 2024/4/14 23:37
 * @Version v1.0
 **/
public interface ZsRefArticleTagManager extends IService<ZsRefArticleTagDO> {

    List<String> getArticleIdList(String tagId);

    List<ZsRefArticleTagDO> getByArticleIdList(List<String> articleIdList);
}
