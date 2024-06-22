package com.zj.zs.service;

import com.zj.zs.domain.dto.page.ZsFriendsDto;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ZsHomeService
 * @Author zj
 * @Description
 * @Date 2024/4/20 15:16
 * @Version v1.0
 **/
public interface ZsHomeService {

    Map<String, List<ZsFriendsDto>> getFriendsList();
}
