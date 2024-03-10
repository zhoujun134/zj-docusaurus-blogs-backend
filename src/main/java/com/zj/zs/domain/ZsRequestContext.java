package com.zj.zs.domain;

import com.zj.zs.domain.dto.UserInfoDto;

import java.util.Objects;

public class ZsRequestContext {

    private static final ThreadLocal<UserInfoDto> curUserContext = new ThreadLocal<>();

    public static void removeAll() {
        curUserContext.remove();
    }

    public static void put(UserInfoDto curContext) {
        if (Objects.isNull(curContext)) {
            return;
        }
        curUserContext.set(curContext);
    }

    public static UserInfoDto getCurContext() {
        UserInfoDto userInfoDto = curUserContext.get();
        if (Objects.isNull(userInfoDto)) {
            return new UserInfoDto();
        }
        return userInfoDto;
    }
}
