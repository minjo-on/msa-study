package com.nninjoon.userservice.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// @AllArgsConstructor
// @NoArgsConstructor
public record Greeting(
	@Value("${greeting.message}")
	String message
) {
}
