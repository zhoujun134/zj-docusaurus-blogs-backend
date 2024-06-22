package com.zj.zs.service.impl;

import com.zj.zs.converter.ArticleConverter;
import com.zj.zs.dao.ZsFriendsManager;
import com.zj.zs.domain.dto.page.ZsFriendsDto;
import com.zj.zs.domain.entity.ZsFriendsDO;
import com.zj.zs.service.ZsHomeService;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ZsHomeServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/20 15:19
 * @Version v1.0
 **/
@Service
@Slf4j
public class ZsHomeServiceImpl implements ZsHomeService {

    @Resource
    private ZsFriendsManager zsFriendsManager;
    @Resource
    private ArticleConverter articleConverter;

    @Override
    public Map<String, List<ZsFriendsDto>> getFriendsList() {
        List<ZsFriendsDO> resultList = zsFriendsManager.list();
        if (CollectionUtils.isEmpty(resultList)) {
            return Collections.emptyMap();
        }
        return Safes.of(resultList).stream()
                .map(articleConverter::toZsFriendsDto)
                .collect(Collectors.groupingBy(ZsFriendsDto::getCategoryName));
    }
}
