package com.nninjoon.orderservice.dto;

import lombok.Builder;

@Builder
public record KafkaOrderDto(
	Schema schema,
	Payload payload
) {
	public static KafkaOrderDto of(Schema schema, Payload payload) {
		return KafkaOrderDto.builder()
			.schema(schema)
			.payload(payload)
			.build();
	}
}