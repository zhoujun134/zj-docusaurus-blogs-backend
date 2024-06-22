package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.ZsUserDO;

public interface UserManager extends IService<ZsUserDO> {

    ZsUserDO getByUsername(String userName);

    ZsUserDO getByUsernameOrEmail(String username, String email);
}
