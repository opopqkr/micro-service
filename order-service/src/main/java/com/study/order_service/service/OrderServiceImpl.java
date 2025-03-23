package com.study.order_service.service;

import com.study.order_service.dto.OrderDto;
import com.study.order_service.entity.OrderEntity;
import com.study.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper mapper;

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(String userId, OrderDto orderDto) {
        orderDto.createOrderInfo(userId);

        OrderEntity createdOrder = orderRepository.save(mapper.map(orderDto, OrderEntity.class));
        return mapper.map(createdOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Not found order"));
        return mapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
