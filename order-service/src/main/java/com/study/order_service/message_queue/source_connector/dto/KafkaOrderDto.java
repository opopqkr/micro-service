package com.study.order_service.message_queue.source_connector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@ToString
public class KafkaOrderDto implements Serializable {

    private Schema schema;

    private Payload payload;

}
