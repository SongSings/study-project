package com.jun.observer.domain;

import lombok.Data;

/**
 * @Author: song jun
 * @CreateTime: 2022-11-22 14:51
 * @Description: 事件消息对象
 */
@Data
public class PlaceOrderEventMessage {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 下单用户ID
     */
    private String userId;
}
