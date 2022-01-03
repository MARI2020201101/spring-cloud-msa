package com.mariworld.orderservice.controller;

import com.mariworld.orderservice.service.OrderService;
import com.mariworld.orderservice.vo.OrderDto;
import com.mariworld.orderservice.vo.OrderEntity;
import com.mariworld.orderservice.vo.RequestOrder;
import com.mariworld.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @PostMapping("/{user-id}/v2/orders")
    public ResponseEntity<ResponseOrder> createOrderV2(@PathVariable("user-id") String userId,
                                                     @RequestBody RequestOrder requestOrder){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto order = orderService.createOrderWithKafka(orderDto);
        ResponseOrder responseOrder = modelMapper.map(order, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{user-id}/orders")
    public ResponseEntity<List<ResponseOrder>> findAllByUserId(@PathVariable("user-id") String userId){
        log.info("before controller findAllByUserId");
        Iterable<OrderEntity> orderEntities = orderService.getOrdersByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ResponseOrder> orderList = new ArrayList<>();
        orderEntities.forEach(o->
                orderList.add(modelMapper.map(o,ResponseOrder.class))
                );
        log.info("after controller findAllByUserId");
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

}
