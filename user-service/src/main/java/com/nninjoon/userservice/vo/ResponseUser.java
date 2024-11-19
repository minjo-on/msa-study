package com.nninjoon.userservice.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nninjoon.userservice.domain.UserEntity;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseUser(
	String email,
	String name,
	String userId,

	List<ResponseOrder> orders
) {
	public static ResponseUser from(UserEntity user, List<ResponseOrder> orders){
		return ResponseUser.builder()
			.email(user.getEmail())
			.name(user.getName())
			.userId(user.getUserId())
			.orders(orders)
			.build();
	}
}
