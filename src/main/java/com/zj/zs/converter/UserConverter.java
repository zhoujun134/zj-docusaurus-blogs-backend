package com.zj.zs.converter;

import com.zj.zs.domain.dto.request.RegisterUserInfoReqDto;
import com.zj.zs.domain.entity.ZsUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @ClassName UserConverter
 * @Author zj
 * @Description
 * @Date 2024/3/10 16:11
 * @Version v1.0
 **/
@Mapper(componentModel="spring")
public interface UserConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.username", target = "username")
    @Mapping(target = "password", expression = "java(cn.dev33.satoken.secure.SaSecureUtil.aesEncrypt(aesKey, request.getPassword()))")
    @Mapping(source = "request.email", target = "email")
    @Mapping(source = "request.description", target = "description")
    ZsUserDO toUserDO(RegisterUserInfoReqDto request, String aesKey);
}
