package com.nninjoon.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestLogin(
	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email not be less than two characters")
	@Email
	String email,

	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Password not be equals or greater than 8 characters")
	String password
) {
}
