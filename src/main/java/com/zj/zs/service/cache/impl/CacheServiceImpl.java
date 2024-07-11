package com.zj.zs.service.cache.impl;

import com.zj.zs.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

/**
 * @ClassName CacheServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/25 22:52
 * @Version v1.0
 **/
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    @SuppressWarnings("all")
    public <T> T get(String key, Class<T> tClass) {
        Object data = get(key);
        if (Objects.nonNull(data) && data.getClass().equals(tClass)) {
            return (T) data;
        }
        return null;
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setExpire(String key, Object value, long expireSeconds) {
        redisTemplate.opsForValue().set(key, String.valueOf(value), Duration.ofSeconds(expireSeconds));
    }

    @Override
    public Object getExpire(String key, long expire) {
        return redisTemplate.opsForValue().getAndExpire(key, Duration.ofSeconds(expire));
    }

    @Override
    public Long getCountOrSet(String key, int expireSeconds) {
        if (StringUtils.isBlank(key)) {
            return 0L;
        }
        Long increment = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofSeconds(expireSeconds));
        if (Objects.isNull(increment)) {
            return 0L;
        }
        return increment;
    }

    @Override
    public boolean exist(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        Boolean existRes = redisTemplate.hasKey(key);
        if (Objects.isNull(existRes)) {
            return false;
        }
        return existRes;
    }
}
