package com.mariworld.orderservice.service;

import com.mariworld.orderservice.jpa.OrderRepository;
import com.mariworld.orderservice.messagequeue.KafkaProducer;
import com.mariworld.orderservice.vo.OrderDto;
import com.mariworld.orderservice.vo.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        OrderEntity save = orderRepository.save(orderEntity);
        OrderDto returendOrder = modelMapper.map(save, OrderDto.class);
        return returendOrder;
    }

    @Override
    public OrderDto createOrderWithKafka(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice()*orderDto.getQty());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        OrderEntity save = orderRepository.save(orderEntity);

        kafkaProducer.send("example-catalog-topic" , orderDto);

        OrderDto returendOrder = modelMapper.map(save, OrderDto.class);
        return returendOrder;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
