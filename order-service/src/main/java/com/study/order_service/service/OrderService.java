package com.study.order_service.service;

import com.study.order_service.dto.OrderDto;
import com.study.order_service.entity.OrderEntity;

public interface OrderService {

    OrderDto createOrder(String userId, OrderDto orderDto);

    OrderDto getOrderByOrderId(String orderId);

    Iterable<OrderEntity> getOrdersByUserId(String userId);

}
