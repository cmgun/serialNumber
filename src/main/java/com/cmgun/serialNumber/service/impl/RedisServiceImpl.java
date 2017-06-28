package com.cmgun.serialNumber.service.impl;

import com.cmgun.serialNumber.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 *
 * @author cmgun
 * @since 2017/6/26
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> stringOpsForValue;

    @Override
    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Long increment(String key, long delta, long timeout, TimeUnit unit) {
        Long value = increment(key, delta);
        // 检查过期时间
        long expire = getExpire(key);
        if (expire < 0) {
            // 添加过期时间
            stringRedisTemplate.expire(key, timeout, unit);
        }
        return value;
    }

    @Override
    public Long increment(String key, long delta) {
        return stringOpsForValue.increment(key, delta);
    }
}