package com.study.order_service.message_queue.source_connector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
@ToString
public class Payload implements Serializable {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("product_id")
    private String productId;

    private int qty;

    @JsonProperty("unit_price")
    private int unitPrice;

    @JsonProperty("total_price")
    private int totalPrice;

}
