package com.study.user_service.client;

import com.study.user_service.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/{userId}/orders") // 호출하려는 micro-service 의 endpoint url 명시
    List<ResponseOrder> getOrders(@PathVariable String userId);

    // @GetMapping("/{userId}/orders_error") // 예외처리 테스트를 위해, 존재하지 않은 api uri 호출.
    // List<ResponseOrder> getOrders(@PathVariable String userId);

}
