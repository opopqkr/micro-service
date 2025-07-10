package com.study.order_service.message_queue.source_connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.order_service.dto.OrderDto;
import com.study.order_service.message_queue.source_connector.dto.Field;
import com.study.order_service.message_queue.source_connector.dto.KafkaOrderDto;
import com.study.order_service.message_queue.source_connector.dto.Payload;
import com.study.order_service.message_queue.source_connector.dto.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Schema schema = Schema.builder()
            .type("struct")
            .fields(Arrays.asList(
                    new Field("string", Boolean.TRUE, "order_id"),
                    new Field("string", Boolean.TRUE, "user_id"),
                    new Field("string", Boolean.TRUE, "product_id"),
                    new Field("int32", Boolean.TRUE, "qty"),
                    new Field("int32", Boolean.TRUE, "unit_price"),
                    new Field("int32", Boolean.TRUE, "total_price")))
            .optional(Boolean.FALSE)
            .name("orders")
            .build();

    public OrderDto createOrder(String topic, String userId, OrderDto orderDto) {
        /* order info create */
        orderDto.createOrderInfo(userId);

        Payload payload = Payload.builder()
                .orderId(orderDto.getOrderId())
                .userId(orderDto.getUserId())
                .productId(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unitPrice(orderDto.getUnitPrice())
                .totalPrice(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Order Producer sent data from Order micro service : {}", kafkaOrderDto);

        return orderDto;
    }
}
