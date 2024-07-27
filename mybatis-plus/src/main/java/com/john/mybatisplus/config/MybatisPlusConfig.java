package com.john.mybatisplus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 默认配置MybatisPlus分页插件，通过conditional注解达到覆盖效用
 *
 */
@Configuration
@MapperScan({ "com.john.**.mapper" })
public class MybatisPlusConfig {



}
