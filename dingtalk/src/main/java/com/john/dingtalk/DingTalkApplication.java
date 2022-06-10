package com.john.dingtalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DingTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingTalkApplication.class, args);
    }

}
