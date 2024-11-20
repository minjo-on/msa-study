package com.nninjoon.orderservice.dto;

import java.io.Serializable;

import com.nninjoon.orderservice.domain.OrderEntity;

import lombok.Builder;

@Builder
public record OrderDto(
	String productId,
	Integer qty,
	Integer unitPrice,
	Integer totalPrice,

	String orderId,
	String userId
) implements Serializable {
	public static OrderDto from(OrderEntity order) {
		return OrderDto.builder()
			.productId(order.getProductId())
			.qty(order.getQty())
			.unitPrice(order.getUnitPrice())
			.totalPrice(order.getTotalPrice())
			.orderId(order.getOrderId())
			.userId(order.getUserId())
			.build();
	}

	public static OrderDto of(String productId, Integer qty, Integer unitPrice, String orderId, String userId) {
		return OrderDto.builder()
			.productId(productId)
			.qty(qty)
			.unitPrice(unitPrice)
			.totalPrice(unitPrice * qty)
			.orderId(orderId)
			.userId(userId)
			.build();
	}
}