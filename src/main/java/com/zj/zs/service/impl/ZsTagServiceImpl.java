package com.zj.zs.service.impl;

import com.zj.zs.converter.ArticleConverter;
import com.zj.zs.dao.ZsCategoryManager;
import com.zj.zs.dao.ZsTagManager;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.domain.entity.ZsCategoryDO;
import com.zj.zs.domain.entity.ZsTagDO;
import com.zj.zs.service.ZsTagService;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ZsTagServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:24
 * @Version v1.0
 **/
@Service
@Slf4j
public class ZsTagServiceImpl implements ZsTagService {

    @Resource
    private ZsTagManager zsTagManager;

    @Resource
    private ArticleConverter converter;

    @Override
    public List<TagDto> listAll(ZsSearchReqDto request) {
        final List<ZsTagDO> list = zsTagManager.list(request);
        return Safes.of(list).stream().map(converter::toTagDTO)
                .collect(Collectors.toList());
    }
}
