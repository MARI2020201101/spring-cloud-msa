package com.mariworld.orderservice.controller;

import com.mariworld.orderservice.service.OrderService;
import com.mariworld.orderservice.vo.OrderDto;
import com.mariworld.orderservice.vo.OrderEntity;
import com.mariworld.orderservice.vo.RequestOrder;
import com.mariworld.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{user-id}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("user-id") String userId,
                                                           @RequestBody RequestOrder requestOrder){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto order = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = modelMapper.map(order, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{user-id}/orders")
    public ResponseEntity<List<ResponseOrder>> findAllByUserId(@PathVariable("user-id") String userId){
        Iterable<OrderEntity> orderEntities = orderService.getOrdersByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ResponseOrder> orderList = new ArrayList<>();
        orderEntities.forEach(o->
                orderList.add(modelMapper.map(o,ResponseOrder.class))
                );
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

}