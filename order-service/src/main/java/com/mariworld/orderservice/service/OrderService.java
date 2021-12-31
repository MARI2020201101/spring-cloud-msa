package com.mariworld.orderservice.service;

import com.mariworld.orderservice.vo.OrderDto;
import com.mariworld.orderservice.vo.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);

}
