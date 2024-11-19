package com.nninjoon.userservice.vo;

import java.time.LocalDateTime;

public record ResponseOrder(
	String productId,
	Integer qty,
	Integer unitPrice,
	Integer totalPrice,
	LocalDateTime createdAt,

	String orderId
) {
}
