package com.study.order_service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {

    private String productId;

    private Integer qty;

    // FIXME - 임시 단가 설정 (카탈로그 서비스 연동 전)
    private Integer unitPrice;

}
