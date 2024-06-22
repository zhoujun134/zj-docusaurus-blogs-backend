package com.zj.zs.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName CommentLevelEnum
 * @Author zj
 * @Description
 * @Date 2024/4/20 23:31
 * @Version v1.0
 **/
@Getter
@AllArgsConstructor
public enum CommentLevelEnum {

    ONE_LEVEL("ONE_LEVEL", "一级"),
    TWO_LEVEL("TWO_LEVEL", "二级"),
    MANY_LEVEL("MANY_LEVEL", "多级"),
    DEFAULT_LEVEL("DEFAULT_LEVEL", "默认级别"),
    ;

    private final String code;

    private final String name;
}
