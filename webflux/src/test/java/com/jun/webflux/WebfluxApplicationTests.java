package com.jun.webflux;

import com.jun.webflux.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@SpringBootTest
class WebfluxApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() throws InterruptedException {
        Mono<?> mono = userService.findById(1L);
        System.out.println("11111");
        Thread.sleep(5000L);

        System.out.println("m2222 " );
    }

}
