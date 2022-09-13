package com.example.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jun
 */
@SpringBootApplication
@MapperScan("com.example.elasticsearch.dao")
@ComponentScan({"com.example.elasticsearch.config"})
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class, args);
    }

}
