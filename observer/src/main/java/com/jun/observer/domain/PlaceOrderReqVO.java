package com.jun.observer.domain;

import lombok.Data;

/**
 * @Author: song jun
 * @CreateTime: 2022-11-22 15:08
 * @Description: 请求参数
 */
@Data
public class PlaceOrderReqVO {

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
