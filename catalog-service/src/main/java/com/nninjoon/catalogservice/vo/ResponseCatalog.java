package com.nninjoon.catalogservice.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nninjoon.catalogservice.domain.CatalogEntity;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseCatalog(
	String productId,
	String productName,
	int stock,
	int unitPrice,
	Date createAt
) {
	public static ResponseCatalog from(CatalogEntity catalogEntity) {
		return ResponseCatalog.builder()
			.productId(catalogEntity.getProductId())
			.productName(catalogEntity.getProductName())
			.stock(catalogEntity.getStock())
			.unitPrice(catalogEntity.getUnitPrice())
			.createAt(catalogEntity.getCreateAt())
			.build();
	}
}
