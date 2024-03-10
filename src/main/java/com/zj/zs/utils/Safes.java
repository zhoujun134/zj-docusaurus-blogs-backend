package com.zj.zs.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName Safes
 * @Author zj
 * @Description
 * @Date 2024/3/10 15:29
 * @Version v1.0
 **/
public class Safes {

    public static <T> T first(Collection<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream()
                .findFirst()
                .orElse(null);
    }

    public static <T> List<T> of(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data;
    }
}
