package com.jun.observer.listen;

import com.jun.observer.event.PlaceOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: song jun
 * @CreateTime: 2022-11-22 15:15
 * @Description: 事件监听者
 * 事件监听者，有两种实现方式，一种是实现ApplicationListener接口，另一种是使用@EventListener注解。
 */
@Service
@Slf4j
public class OrderMetricsListener {

    /**
     * 使用@EventListener注解 监听
     * 异步执行也非常简单，使用Spring的异步注解@Async就可以了。例如：
     * @param event
     */
    @EventListener
    @Async
    public void metrics(PlaceOrderEvent event) {
        log.info("[afterPlaceOrder] listener metrics");
    }
}
