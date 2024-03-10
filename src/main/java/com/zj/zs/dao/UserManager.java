package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.UserDO;

public interface UserManager extends IService<UserDO> {

    UserDO getByUsername(String userName);

    UserDO getByUsernameOrEmail(String username, String email);
}
