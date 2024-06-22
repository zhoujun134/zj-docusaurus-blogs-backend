package com.zj.zs.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

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
    public static <T> Optional<T> firstT(Collection<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Optional.empty();
        }
        return data.stream()
                .findFirst();
    }

    public static <T> T randOne(Collection<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        Random random = new Random();
        int nextInt = random.nextInt(1, data.size());
        if (nextInt >= data.size()) {
            nextInt = nextInt % data.size();
        }
        return new ArrayList<>(data).get(nextInt);
    }

    public static <T> List<T> of(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data;
    }

    public static <T> Stream<T> streamOf(T[] data) {
        if (data == null) {
            return Stream.empty();
        }
        return Stream.of(data);
    }
    public static <T> Set<T> of(Set<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptySet();
        }
        return data;
    }

    public static <T, E> Map<T, E> of(Map<T, E> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyMap();
        }
        return data;
    }
}
