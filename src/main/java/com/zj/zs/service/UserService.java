package com.zj.zs.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.zj.zs.domain.dto.request.LoginUserReqDTO;
import com.zj.zs.domain.dto.request.RegisterUserInfoReqDTO;

public interface UserService {

    SaTokenInfo login(LoginUserReqDTO request);

    void logout();

    Boolean register(RegisterUserInfoReqDTO request);

}
