package com.zj.zs.utils;

import java.util.UUID;

/**
 * @ClassName UUIDUtils
 * @Author zj
 * @Description
 * @Date 2024/4/14 23:04
 * @Version v1.0
 **/
public class UUIDUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
