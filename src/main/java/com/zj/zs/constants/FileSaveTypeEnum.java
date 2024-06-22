package com.zj.zs.constants;

import lombok.Getter;

/**
 * @ClassName FileSaveTypeEnum
 * @Author zj
 * @Description
 * @Date 2024/4/27 01:07
 * @Version v1.0
 **/
@Getter
public enum FileSaveTypeEnum {

    /**
     * 腾讯对象文件
     */
    TENCENT_OSS,
    /**
     * 网站静态资源文件
     */
    STATIC_FILE,
}
