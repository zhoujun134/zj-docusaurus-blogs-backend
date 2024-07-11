package com.zj.zs.service.cache;

/**
 * @ClassName CacheService
 * @Author zj
 * @Description
 * @Date 2024/4/25 22:49
 * @Version v1.0
 **/
public interface CacheService {

    void set(String key, Object value);

    String get(String key);

    <T> T get(String key, Class<T> tClass);

    void del(String key);

    void setExpire(String key, Object value, long expire);

    Object getExpire(String key, long expire);

    Long getCountOrSet(String key, int expireSeconds);

    boolean exist(String key);
}
