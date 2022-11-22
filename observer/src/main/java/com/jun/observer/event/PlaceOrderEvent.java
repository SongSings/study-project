package com.jun.observer.event;

import com.jun.observer.domain.PlaceOrderEventMessage;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: song jun
 * @CreateTime: 2022-11-22 15:11
 * @Description: 自定义事件
 * PlaceOrderEvent：继承ApplicationEvent，并重写构造函数。ApplicationEvent是Spring提供的所有应用程序事件扩展类。
 */
public class PlaceOrderEvent extends ApplicationEvent {
    public PlaceOrderEvent(PlaceOrderEventMessage source) {
        super(source);
    }
}
