package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.UserManager;
import com.zj.zs.dao.mapper.UserMapper;
import com.zj.zs.domain.entity.ZsUserDO;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserManagerImpl extends ServiceImpl<UserMapper, ZsUserDO> implements UserManager {

    @Override
    public ZsUserDO getByUsername(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        final List<ZsUserDO> result = lambdaQuery()
                .eq(ZsUserDO::getUsername, userName)
                .list();
        return Safes.first(result);
    }

    @Override
    public ZsUserDO getByUsernameOrEmail(String username, String email) {
        if (StringUtils.isAllBlank(username, email)) {
            return null;
        }
        final List<ZsUserDO> result = lambdaQuery()
                .eq(StringUtils.isNoneBlank(username), ZsUserDO::getUsername, username)
                .or(StringUtils.isBlank(email))
                .eq(ZsUserDO::getEmail, email)
                .list();
        return Safes.first(result);
    }
}
