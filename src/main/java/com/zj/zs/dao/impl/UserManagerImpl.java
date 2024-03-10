package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.UserManager;
import com.zj.zs.dao.mapper.UserMapper;
import com.zj.zs.domain.entity.UserDO;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserManagerImpl extends ServiceImpl<UserMapper, UserDO> implements UserManager {

    @Override
    public UserDO getByUsername(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        final List<UserDO> result = lambdaQuery()
                .eq(UserDO::getUsername, userName)
                .list();
        return Safes.first(result);
    }

    @Override
    public UserDO getByUsernameOrEmail(String username, String email) {
        if (StringUtils.isAllBlank(username, email)) {
            return null;
        }
        final List<UserDO> result = lambdaQuery()
                .eq(StringUtils.isNoneBlank(username), UserDO::getUsername, username)
                .or(StringUtils.isBlank(email))
                .eq(UserDO::getEmail, email)
                .list();
        return Safes.first(result);
    }
}
