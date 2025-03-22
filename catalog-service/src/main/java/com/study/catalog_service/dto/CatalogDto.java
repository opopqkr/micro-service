package com.study.catalog_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDto implements Serializable {

    private String productId;

    private Integer qty;

    private Integer unitPrice;

    private Integer totalPrice;

    private String orderId;

    private String userId;

}
