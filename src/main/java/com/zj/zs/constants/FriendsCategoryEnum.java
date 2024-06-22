package com.zj.zs.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

/**
 * @ClassName FriendsCategoryEnum
 * @Author zj
 * @Description
 * @Date 2024/4/20 15:21
 * @Version v1.0
 **/
@Getter
@AllArgsConstructor
public enum FriendsCategoryEnum {
    FRIENDS_SITE("FRIENDS_SITE", "我的友链"),
    TOOLS_SITE("TOOLS_SITE", "我的工具组"),
    ;

    private final String code;

    private final String name;


    public static String getNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        return Stream.of(FriendsCategoryEnum.values())
                .filter(friendsCategoryEnum -> StringUtils.equals(code, friendsCategoryEnum.getCode()))
                .findFirst()
                .map(FriendsCategoryEnum::getName)
                .orElse("");
    }
}
