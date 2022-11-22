package com.jun.observer.services.impl;

import com.jun.observer.domain.PlaceOrderEventMessage;
import com.jun.observer.domain.PlaceOrderReqVO;
import com.jun.observer.domain.PlaceOrderResVO;
import com.jun.observer.event.PlaceOrderEvent;
import com.jun.observer.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Author: song jun
 * @CreateTime: 2022-11-22 15:03
 * @Description: 订单实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 下单
     */
    @Override
    public PlaceOrderResVO placeOrder(PlaceOrderReqVO reqVO) {
        log.info("[placeOrder] start.");
        PlaceOrderResVO resVO = new PlaceOrderResVO();
        //消息
        PlaceOrderEventMessage eventMessage = new PlaceOrderEventMessage();
        //发布事件 在Idea里查看事件的监听者也比较方便，点击左边图标，就可以查看监听者。
        applicationEventPublisher.publishEvent(new PlaceOrderEvent(eventMessage));
        log.info("[placeOrder] end.");
        return resVO;
    }

}
