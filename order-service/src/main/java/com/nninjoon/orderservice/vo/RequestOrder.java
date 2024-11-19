package com.nninjoon.orderservice.vo;

public record RequestOrder(
	String productId,
	Integer qty,
	Integer unitPrice
) { }
