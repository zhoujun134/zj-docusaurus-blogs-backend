package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.domain.entity.ZsCategoryDO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ZsCategoryManager
 * @Author zj
 * @Description
 * @Date 2024/4/14 21:55
 * @Version v1.0
 **/
public interface ZsCategoryManager extends IService<ZsCategoryDO> {

    List<ZsCategoryDO> list(ZsSearchReqDto request);

    boolean createRefInfo(String articleId, List<String> categoryIdList);

    List<ZsCategoryDO> listByNames(List<String> categoryNames);

    /**
     * 批量查询文章的 ZsCategoryDO 信息
     * @param articleIdList 文章id列表
     * @return key 为文章id，value 为 ZsCategoryDO 列表
     */
    Map<String, List<ZsCategoryDO>> getByArticleIdList(List<String> articleIdList);

    List<ZsCategoryDO> getByArticleId(String articleId);
}
