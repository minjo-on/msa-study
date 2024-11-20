package com.nninjoon.orderservice.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record Payload(
	String order_id,
	String user_id,
	String product_id,
	int qty,
	int unit_price,
	int total_price,
	LocalDateTime created_at
) {
}