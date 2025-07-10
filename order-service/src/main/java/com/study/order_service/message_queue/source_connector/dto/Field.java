package com.study.order_service.message_queue.source_connector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Field {

    private String type;

    private boolean optional;

    private String field;

}
