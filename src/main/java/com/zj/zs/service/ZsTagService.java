package com.zj.zs.service;

import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;

import java.util.List;

/**
 * @ClassName ZsTagService
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:24
 * @Version v1.0
 **/
public interface ZsTagService {

    List<TagDto> listAll(ZsSearchReqDto request);
}
