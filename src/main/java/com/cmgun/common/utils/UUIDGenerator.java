package com.cmgun.common.utils;

import com.cmgun.serialNumber.config.SerialNumberContextHelper;
import com.cmgun.serialNumber.generator.AbstractSerialNumberGenerator;
import org.springframework.stereotype.Component;

/**
 * UUID生成器 <br>
 * <br>
 * 新增流水号生成，所有UUID都改为使用redis方式的流水号生成器 <br>
 * Updated by cmgun on 2017/5/26 <br>
 */
@Component
public class UUIDGenerator {

    /**
     * 新的UUID生成规则，返回18位的流水号 <br>
     * created by chenqilin <br>
     * @return
     */
    public static String generate() {
        AbstractSerialNumberGenerator serialNumberGenerator = SerialNumberContextHelper.getSerialNumberGenerator();
        return serialNumberGenerator.get();
    }

    /**
     * 新的UUID生成规则，返回18位的流水号 <br>
     * created by chenqilin <br>
     * @param prefix 自定义的前缀，如果为null，使用默认前缀 <br>
     *               注意：自定义前缀的长度必须小于或等于配置里的前缀长度，小于时高位补0
     * @return
     */
    public static String generate(String prefix) {
        AbstractSerialNumberGenerator serialNumberGenerator = SerialNumberContextHelper.getSerialNumberGenerator();
        return serialNumberGenerator.get(prefix);
    }

    /**
     * 生成特定长度的自增流水号，如8位的ClientId
     * @param length 流水号长度
     * @param maxLength 流水号最大长度
     * @param prefix 业务标记
     * @param delta 自增量
     * @return
     */
    public static String generate(String prefix, int length, int maxLength,long delta) {
        AbstractSerialNumberGenerator serialNumberGenerator = SerialNumberContextHelper.getSerialNumberGenerator();
        return serialNumberGenerator.getAutoIncrement(prefix, length, maxLength, delta);
    }
}
