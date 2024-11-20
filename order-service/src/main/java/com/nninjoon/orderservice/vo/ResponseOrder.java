package com.nninjoon.orderservice.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nninjoon.orderservice.domain.OrderEntity;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseOrder(
	String productId,
	Integer qty,
	Integer unitPrice,
	Integer totalPrice,
	LocalDateTime createdAt,

	String orderId
) {
	public static ResponseOrder from(OrderEntity orderEntity) {
		return ResponseOrder.builder()
			.productId(orderEntity.getProductId())
			.qty(orderEntity.getQty())
			.unitPrice(orderEntity.getUnitPrice())
			.totalPrice(orderEntity.getTotalPrice())
			.createdAt(orderEntity.getCreatedAt())
			.orderId(orderEntity.getOrderId())
			.build();
	}

	public static ResponseOrder of(String productId, Integer qty, Integer unitPrice, String orderId) {
		return ResponseOrder.builder()
			.productId(productId)
			.qty(qty)
			.unitPrice(unitPrice)
			.totalPrice(qty * unitPrice)
			.createdAt(LocalDateTime.now())
			.orderId(orderId)
			.build();
	}
}
