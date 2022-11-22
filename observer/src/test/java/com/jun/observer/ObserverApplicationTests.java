package com.jun.observer;

import com.jun.observer.domain.PlaceOrderReqVO;
import com.jun.observer.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ObserverApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void placeOrder() {
        PlaceOrderReqVO placeOrderReqVO = new PlaceOrderReqVO();
        orderService.placeOrder(placeOrderReqVO);
    }

}
