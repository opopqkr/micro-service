package com.study.user_service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrder {

    private String productId;

    private Integer qty;

    private Integer unitPrice;

    private Integer totalPrice;

    private LocalDateTime createdAt;

    private String orderId;

}
