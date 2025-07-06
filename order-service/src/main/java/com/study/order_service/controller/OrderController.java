package com.study.order_service.controller;

import com.study.order_service.dto.OrderDto;
import com.study.order_service.entity.OrderEntity;
import com.study.order_service.message_queue.KafkaProducer;
import com.study.order_service.service.OrderService;
import com.study.order_service.vo.RequestOrder;
import com.study.order_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;

    private final ModelMapper mapper;

    private final OrderService orderService;

    private final KafkaProducer kafkaProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    // http://127.0.0.1:0/order-service/{userId}/orders
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder requestOrder) {
        OrderDto orderDto = mapper.map(requestOrder, OrderDto.class);
        OrderDto createdOrder = orderService.createOrder(userId, orderDto);

        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        /* send this order to the kafka */
        kafkaProducer.send("example-catalog-topic", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(order -> {
            result.add(mapper.map(order, ResponseOrder.class));
        });

        return ResponseEntity.ok(result);
    }

}
