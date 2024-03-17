package com.zj.zs.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.zj.zs.converter.UserConverter;
import com.zj.zs.dao.UserManager;
import com.zj.zs.domain.dto.request.LoginUserReqDTO;
import com.zj.zs.domain.dto.request.RegisterUserInfoReqDTO;
import com.zj.zs.domain.entity.UserDO;
import com.zj.zs.service.UserService;
import com.zj.zs.utils.exception.ResultCode;
import com.zj.zs.utils.exception.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserManager userManager;

    @Resource
    private UserConverter userConverter;

    @Value("${zs.boot.aes.key:zs-boot}")
    private String aesKey;

    @Override
    public SaTokenInfo login(LoginUserReqDTO request) {
        final String passwordInfo = SaSecureUtil.aesEncrypt(aesKey, request.getPassword());
        final UserDO userInfo = userManager.getByUsername(request.getUsername());
        ValidateUtil.exceptionByTrue(Objects.isNull(userInfo)
                || !StringUtils.equals(userInfo.getPassword(), passwordInfo), ResultCode.LOGIN_USER_INFO_ERROR);
        StpUtil.login(userInfo.getUsername());
        return StpUtil.getTokenInfo();
    }

    @Override
    public void logout() {
        try {
            StpUtil.logout();
        } catch (Exception e) {
            log.info("UserServiceImpl######logout: 登出异常: error:{}", e.getMessage(), e);
        }
    }

    @Override
    public Boolean register(RegisterUserInfoReqDTO request) {
        final UserDO userDO = userConverter.toUserDO(request, aesKey);
        final UserDO existUser = userManager.getByUsernameOrEmail(userDO.getUsername(), userDO.getEmail());
        ValidateUtil.exceptionByTrue(Objects.nonNull(existUser),
                ResultCode.REGISTER_EMAIL_INFO_EXISTED);
        return userManager.save(userDO);
    }
}
