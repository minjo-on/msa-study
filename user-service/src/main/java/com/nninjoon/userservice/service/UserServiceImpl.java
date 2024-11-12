package com.nninjoon.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nninjoon.userservice.domain.UserEntity;
import com.nninjoon.userservice.repository.UserRepository;
import com.nninjoon.userservice.dto.UserDto;
import com.nninjoon.userservice.vo.RequestUser;
import com.nninjoon.userservice.vo.ResponseUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public ResponseUser createUser(RequestUser request) {
		String userId = UUID.randomUUID().toString();
		String password = passwordEncoder.encode(request.password());
		UserEntity userEntity = UserEntity.create(request.email(), request.name(), userId, password);

		userRepository.save(userEntity);
		ResponseUser result = ResponseUser.from(userEntity);

		return result;
	}

	@Override
	public UserDto getUserById(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}

		UserDto userDto = UserDto.from(userEntity);

		String orderUrl = "http://localhost:8000/order-service/%s/orders";
		return userDto;
	}

	@Override
	public Iterable<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
}
