package com.zj.zs.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.zj.zs.domain.dto.request.LoginUserReqDto;
import com.zj.zs.domain.dto.request.RegisterUserInfoReqDto;

public interface UserService {

    SaTokenInfo login(LoginUserReqDto request);

    void logout();

    Boolean register(RegisterUserInfoReqDto request);

}
