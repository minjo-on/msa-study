package com.nninjoon.userservice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nninjoon.userservice.domain.UserEntity;
import com.nninjoon.userservice.vo.ResponseOrder;

import lombok.Builder;

@Builder
public record UserDto(
	String email,
	String password,
	String name,
	String userId,
	Date createdAt,

	String encryptedPwd,

	List<ResponseOrder> orders
) {
	public static UserDto from(UserEntity user) {
		return UserDto.builder()
			.email(user.getEmail())
			.password(user.getEncryptedPwd())
			.name(user.getName())
			.userId(user.getUserId())
			.orders(new ArrayList<>())
			.build();
	}

	public UserDto withOrders(List<ResponseOrder> orders) {
		return new UserDto(
			this.email, this.password, this.name, this.userId, this.createdAt, this.encryptedPwd, orders);
	}
}
