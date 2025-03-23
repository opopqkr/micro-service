package com.study.order_service.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto implements Serializable {

    private String orderId;

    private String userId;

    private String productId;

    private Integer qty;

    private Integer unitPrice;

    private Integer totalPrice;

    public void createOrderInfo(String userId) {
        this.userId = userId;
        this.orderId = UUID.randomUUID().toString();
        this.totalPrice = qty * unitPrice;
    }
}