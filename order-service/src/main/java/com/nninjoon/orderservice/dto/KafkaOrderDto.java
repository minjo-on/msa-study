package com.nninjoon.orderservice.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record KafkaOrderDto(
	Schema schema,
	Payload payload
) implements Serializable {
	public static KafkaOrderDto of(Schema schema, Payload payload) {
		return KafkaOrderDto.builder()
			.schema(schema)
			.payload(payload)
			.build();
	}
}