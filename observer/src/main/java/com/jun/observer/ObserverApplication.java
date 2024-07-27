package com.jun.observer;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

/**
 * @author songjun
 * @description 观察者模式demo
 */
@SpringBootApplication
@Async
@EsMapperScan("com.jun.observer.mapper")
public class ObserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObserverApplication.class, args);
    }

}
