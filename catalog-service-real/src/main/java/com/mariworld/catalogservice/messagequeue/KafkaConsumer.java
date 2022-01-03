package com.mariworld.catalogservice.messagequeue;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariworld.catalogservice.jpa.CatalogRepository;
import com.mariworld.catalogservice.vo.CatalogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository  catalogRepository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage){
        log.info("kafkaMessage -> {}", kafkaMessage);
        Map<String, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            map = objectMapper.readValue(kafkaMessage, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String productId =(String) map.get("productId");
        CatalogEntity entity = catalogRepository.findByProductId(productId);
        Integer qty = (Integer) map.get("qty");
        entity.setStock(entity.getStock()-qty);
        catalogRepository.save(entity);
    }

}
