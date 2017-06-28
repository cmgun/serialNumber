package com.cmgun.serialNumber.generator;

import com.cmgun.serialNumber.bean.SerialNumberMode;
import com.cmgun.serialNumber.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 序列号生成器 - Redis模式
 * Created by cmgun on 2017/5/18.
 */
public class SerialNumberRedisGenerator extends AbstractSerialNumberGenerator {

    @Autowired
    private RedisService redisService;

    public SerialNumberRedisGenerator(String prefix, String startNum, String dataFormat, String decimalFormat) {
        super(prefix, startNum, dataFormat, decimalFormat);
    }

    @Override
    public String getMode() {
        return SerialNumberMode.REDIS.toString();
    }

    @Override
    public String get(String prefix) {
        Date now = new Date();
        DateFormat dateFormat = super.getDateFormat();
        StringBuilder key = new StringBuilder(super.getPrefix());
        key.append(":").append(dateFormat.format(now));
        // 目前设置过期时间为一天
        Long number = redisService.increment(key.toString(), 1L, 1, TimeUnit.DAYS);
        return super.generateNumber(prefix, now, number);
    }

    /**
     * 生成指定长度的序列号，特定业务使用，用于生成非前缀+日期+流水号格式的序号
     * @param prefix 业务前缀
     * @param length 需要生成的长度
     * @param maxLength 序列号最大长度
     * @param delta 增量
     * @return
     */
    public String getAutoIncrement(String prefix, int length, int maxLength,long delta) {
        Long value = redisService.increment(prefix, delta);
        return super.parseAutoIncrement(value, length, maxLength);
    }
}
