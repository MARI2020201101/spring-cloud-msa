package com.mariworld.userservice.client;

import com.mariworld.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service")
public interface OrderServiceClient {

    @GetMapping("/order-service/{user-id}/orders")
    List<ResponseOrder> getOrders(@PathVariable("user-id") String userId);

    @GetMapping("/order-service/{user-id}/orders-exception")
    List<ResponseOrder> getOrdersException(@PathVariable("user-id") String userId);
}
