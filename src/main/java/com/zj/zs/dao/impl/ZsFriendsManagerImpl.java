package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.ZsFriendsManager;
import com.zj.zs.dao.mapper.ZsFriendsMapper;
import com.zj.zs.domain.entity.ZsFriendsDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName ZsFriendsManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/20 15:14
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsFriendsManagerImpl extends ServiceImpl<ZsFriendsMapper, ZsFriendsDO> implements ZsFriendsManager {
}
