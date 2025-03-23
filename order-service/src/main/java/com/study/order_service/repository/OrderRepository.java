package com.study.order_service.repository;

import com.study.order_service.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderId(String productId);

    Iterable<OrderEntity> findByUserId(String userId);

}
