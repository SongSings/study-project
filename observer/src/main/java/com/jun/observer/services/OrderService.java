package com.jun.observer.services;

import com.jun.observer.domain.PlaceOrderReqVO;
import com.jun.observer.domain.PlaceOrderResVO;

public interface OrderService {

    PlaceOrderResVO placeOrder(PlaceOrderReqVO reqVO);
}
