package com.zj.zs.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName DocusaurusFileType
 * @Author zj
 * @Description
 * @Date 2024/6/16 23:25
 * @Version v1.0
 **/
@Getter
@AllArgsConstructor
public enum DocusaurusFileTypeEnum {
    DOCS("docs", "文档总结类"),
    BLOG("blog", "博客类"),
    ;

    private final String code;

    private final String name;
}
