package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.domain.entity.ZsTagDO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ZsTagManager
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:23
 * @Version v1.0
 **/
public interface ZsTagManager extends IService<ZsTagDO> {

    List<ZsTagDO> list(ZsSearchReqDto request);
    boolean createRefInfo(String articleId, List<String> tagIdList);

    /**
     * 批量查询文章的 tag 信息
     * @param articleIdList 文章id列表
     * @return key 为文章id，value 为 tag 列表
     */
    Map<String, List<ZsTagDO>> getByArticleIdList(List<String> articleIdList);

    List<ZsTagDO> listByNames(List<String> tagNames);

    List<ZsTagDO> getByArticleId(String articleId);

}
