package com.nninjoon.userservice.service;

import com.nninjoon.userservice.domain.UserEntity;
import com.nninjoon.userservice.dto.UserDto;
import com.nninjoon.userservice.vo.RequestUser;
import com.nninjoon.userservice.vo.ResponseUser;

public interface UserService {
	ResponseUser createUser(RequestUser request);
	UserDto getUserById(String userId);
	Iterable<UserEntity> getAllUsers();
}
