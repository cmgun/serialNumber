package com.cmgun.serialNumber.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Mybatis配置
 *
 * @author cmgun
 * @since 2017/6/26
 */
@Configuration
public class MybatisConfig {

    /**
     * 配置mapper扫描路径和格式
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer configurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setAnnotationClass(Repository.class);
        configurer.setBasePackage("com.cmgun");
        return configurer;
    }

    /**
     * 配置数据源、拦截器、Mapper路径
     *
     * @param dataSource
     * @param properties
     * @return
     * @throws Exception
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource, MybatisProperties properties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 可以在这里增加拦截器

        bean.setMapperLocations(properties.resolveMapperLocations());
        return bean.getObject();
    }

}