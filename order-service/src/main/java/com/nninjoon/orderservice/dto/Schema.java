package com.nninjoon.orderservice.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record Schema(
	String type,
	List<Field> fields,
	boolean optional,
	String name
) {
}
