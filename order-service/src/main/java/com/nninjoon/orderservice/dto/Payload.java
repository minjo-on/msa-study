package com.nninjoon.orderservice.dto;

import lombok.Builder;

@Builder
public record Payload(
	String order_id,
	String user_id,
	String product_id,
	int qty,
	int unit_price,
	int total_price
) {
}