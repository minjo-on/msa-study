package com.nninjoon.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nninjoon.userservice.client.OrderServiceClient;
import com.nninjoon.userservice.domain.UserEntity;
import com.nninjoon.userservice.repository.UserRepository;
import com.nninjoon.userservice.dto.UserDto;
import com.nninjoon.userservice.vo.RequestUser;
import com.nninjoon.userservice.vo.ResponseOrder;
import com.nninjoon.userservice.vo.ResponseUser;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	//private final RestTemplate restTemplate;
	private final Environment env;

	private final OrderServiceClient orderServiceClient;

	@Override
	public ResponseUser createUser(RequestUser request) {
		String userId = UUID.randomUUID().toString();
		String password = passwordEncoder.encode(request.password());
		UserEntity userEntity = UserEntity.create(request.email(), request.name(), userId, password);

		userRepository.save(userEntity);
		ResponseUser result = ResponseUser.from(userEntity, new ArrayList<>());

		return result;
	}

	@Override
	public ResponseUser getUserById(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// Using as rest Template
		// String orderUrl = String.format(env.getProperty("order_service.url"), userId);
		// ResponseEntity<List<ResponseOrder>> orderListResponse =
		// 	restTemplate.exchange(orderUrl, HttpMethod.GET, null,
		// 		new ParameterizedTypeReference<List<ResponseOrder>>() {
		// 	});
		//
		// List<ResponseOrder> orders = orderListResponse.getBody();

		// Using as feign client
		// Feign exception handling
		// List<ResponseOrder> orders = new ArrayList<>();
		// try {
		// 	orders = orderServiceClient.getOrders(userId);
		// }catch (FeignException e) {
		// 	log.error(e.getMessage());
		// }

		// ErrorDecoder
		List<ResponseOrder> orders = orderServiceClient.getOrders(userId);

		return ResponseUser.from(userEntity, orders);
	}

	@Override
	public Iterable<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
}
