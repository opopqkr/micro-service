package com.study.order_service.controller;

import com.study.order_service.dto.OrderDto;
import com.study.order_service.entity.OrderEntity;
import com.study.order_service.message_queue.KafkaProducer;
import com.study.order_service.message_queue.source_connector.OrderProducer;
import com.study.order_service.service.OrderService;
import com.study.order_service.vo.RequestOrder;
import com.study.order_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;

    private final ModelMapper mapper;

    private final OrderService orderService;

    private final KafkaProducer kafkaProducer;

    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    // http://127.0.0.1:0/order-service/{userId}/orders
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder requestOrder) {
        log.info("Before add orders data.");
        OrderDto orderDto = mapper.map(requestOrder, OrderDto.class);

        /* jpa */
        OrderDto createdOrder = orderService.createOrder(userId, orderDto);
        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        /* Generating data via Kafka Source Connector without using jpa */
        //  orderProducer.createOrder("orders", userId, orderDto);

        /* send this order to the kafka */
        // kafkaProducer.send("example-catalog-topic", orderDto);
        // ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        log.info("After added orders data.");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        log.info("Before retrieve orders data.");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(order -> {
            result.add(mapper.map(order, ResponseOrder.class));
        });

        // Zipkin 에서의 장애 모니터링을 위해 예외 발생
        // try {
        //     Thread.sleep(1000);
        //     throw new RuntimeException("장애 발생!!");
        // } catch (InterruptedException e) {
        //     log.warn(e.getMessage());
        // }

        log.info("After retrieve orders data.");
        return ResponseEntity.ok(result);
    }

}
