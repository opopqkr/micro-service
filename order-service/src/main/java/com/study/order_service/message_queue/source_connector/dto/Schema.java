package com.study.order_service.message_queue.source_connector.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Schema {

    private String type;

    private List<Field> fields;

    private boolean optional;

    private String name;

}
