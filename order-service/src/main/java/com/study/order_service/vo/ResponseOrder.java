package com.study.order_service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String orderId;

    private String productId;

    private Integer qty;

    private Integer unitPrice;

    private Integer totalPrice;

    private LocalDateTime createdAt;
    
}
