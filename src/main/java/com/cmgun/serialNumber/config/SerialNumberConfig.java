package com.cmgun.serialNumber.config;

import com.cmgun.serialNumber.generator.AbstractSerialNumberGenerator;
import com.cmgun.serialNumber.generator.SerialNumberDBGenerator;
import com.cmgun.serialNumber.generator.SerialNumberDefaultGenerator;
import com.cmgun.serialNumber.generator.SerialNumberRedisGenerator;
import com.cmgun.serialNumber.bean.SerialNumberInfo;
import com.cmgun.serialNumber.bean.SerialNumberMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * 序列号生成器配置文件
 * Created by cmgun on 2017/5/17.
 */
@Configuration
@EnableConfigurationProperties(SerialNumberInfo.class)
public class SerialNumberConfig {

    /**
     * 流水号主键长度
     */
    private static final int SERIALNUMBER_LENGTH = 18;

    @Autowired
    private SerialNumberInfo serialNumberInfo;

    /**
     * 序列号生成器
     */
    private AbstractSerialNumberGenerator serialNumberGenerator = null;

    /**
     * 根据配置文件初始化序列号生成器
     */
    @Bean("serialNumberGenerator")
    @ConditionalOnMissingBean
    public AbstractSerialNumberGenerator initSerialNumberModeGetter() {
        String open = serialNumberInfo.getOpen();
        if (!StringUtils.isEmpty(open) && Boolean.parseBoolean(open)) {
            // 检查序列号长度是否合法
            checkSerialNumberLength();
            if (SerialNumberMode.DEFAULT.toString().equals(serialNumberInfo.getMode())) {
                // 序列号无序生成，那么就不需要在db或者redis记录当前序列号的编号了
                serialNumberGenerator = new SerialNumberDefaultGenerator(serialNumberInfo.getPrefix(), serialNumberInfo.getStartNum(), serialNumberInfo.getDatePattern(), serialNumberInfo.getNumberPattern());
                return serialNumberGenerator;
            }
            if (SerialNumberMode.DB.toString().equals(serialNumberInfo.getMode())) {
                // DB模式
                serialNumberGenerator = new SerialNumberDBGenerator(serialNumberInfo.getPrefix(), serialNumberInfo.getStartNum(), serialNumberInfo.getDatePattern(), serialNumberInfo.getNumberPattern(), serialNumberInfo.getWaitTime());
                return serialNumberGenerator;
            } else if (SerialNumberMode.REDIS.toString().equals(serialNumberInfo.getMode())) {
                // Redis模式
                serialNumberGenerator = new SerialNumberRedisGenerator(serialNumberInfo.getPrefix(), serialNumberInfo.getStartNum(), serialNumberInfo.getDatePattern(), serialNumberInfo.getNumberPattern());
                return serialNumberGenerator;
            } else {
                // 不存在的模式
                throw new RuntimeException("SerialNumber Mode Illegal");
            }
        } else {
            return null;
        }
    }

    /**
     * 备用序列号生成器
     *
     * @return
     */
    @Bean("backupSerialNumberGenerator")
    public AbstractSerialNumberGenerator initBackupSeralNumberGetter() {
        return new SerialNumberDefaultGenerator(serialNumberInfo.getBackupPrefix(), serialNumberInfo.getStartNum(), serialNumberInfo.getDatePattern(), serialNumberInfo.getNumberPattern());
    }

    /**
     * 加载自定义的SerialNumberContextHelper，用来获取SpringContext里的serialNumberGenerator
     *
     * @return
     */
    @Bean
    public SerialNumberContextHelper getContext() {
        return new SerialNumberContextHelper();
    }

    /**
     * 检查配置里的序列号/备用序列号长度是否为18位
     */
    private void checkSerialNumberLength() {
        StringBuilder key = new StringBuilder();
        key.append(serialNumberInfo.getPrefix())
                .append(serialNumberInfo.getDatePattern())
                .append(serialNumberInfo.getNumberPattern());
        if (key.length() != SERIALNUMBER_LENGTH) {
            throw new RuntimeException("SerialNumber length Illegal");
        }
        StringBuilder backupKey = new StringBuilder();
        backupKey.append(serialNumberInfo.getBackupPrefix())
                .append(serialNumberInfo.getDatePattern())
                .append(serialNumberInfo.getNumberPattern());
        if (backupKey.length() != SERIALNUMBER_LENGTH) {
            throw new RuntimeException("Backup serialNumber length Illegal");
        }
    }

}
