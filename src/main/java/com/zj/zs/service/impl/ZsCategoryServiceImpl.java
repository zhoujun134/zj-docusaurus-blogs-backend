package com.zj.zs.service.impl;

import com.zj.zs.converter.ArticleConverter;
import com.zj.zs.dao.ZsCategoryManager;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.domain.entity.ZsCategoryDO;
import com.zj.zs.service.ZsCategoryService;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ZsCategoryServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 20:27
 * @Version v1.0
 **/
@Service
@Slf4j
public class ZsCategoryServiceImpl implements ZsCategoryService {

    @Resource
    private ZsCategoryManager zsCategoryManager;

    @Resource
    private ArticleConverter converter;
    @Override
    public List<CategoryDto> listAll(ZsSearchReqDto request) {
        final List<ZsCategoryDO> list = zsCategoryManager.list(request);
        return Safes.of(list).stream().map(converter::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
