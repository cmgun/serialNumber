package com.cmgun.serialNumber.service;

import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 *
 * @author cmgun
 * @since 2017/6/26
 */

public interface RedisService {

    /**
     * 获取过期时间
     *
     * @param key key值
     * @return
     */
    long getExpire(String key);

    /**
     * 给key对应的Value做自增
     *
     * @param key     key值
     * @param delta   增量
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return
     */
    Long increment(String key, long delta, long timeout, TimeUnit unit);

    /**
     * 给key对应的Value做自增
     * @param key key值
     * @param delta 增量
     * @return
     */
    Long increment(String key, long delta);
}