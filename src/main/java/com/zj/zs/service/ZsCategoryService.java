package com.zj.zs.service;

import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;

import java.util.List;

/**
 * @ClassName ZsCategoryService
 * @Author zj
 * @Description
 * @Date 2024/4/14 20:26
 * @Version v1.0
 **/
public interface ZsCategoryService {

    List<CategoryDto> listAll(ZsSearchReqDto request);

}
