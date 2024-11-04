package com.nninjoon.catalogservice.dto;

import java.io.Serializable;

import lombok.Builder;

@Builder
public record CatalogDto(
	String productId,
	int qty,
	int unitPrice,
	int totalPrice,

	String orderId,
	String userId
) implements Serializable {

}
