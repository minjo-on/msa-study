package com.nninjoon.userservice;

import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nninjoon.userservice.domain.UserEntity;
import com.nninjoon.userservice.dto.UserDto;
import com.nninjoon.userservice.service.UserService;
import com.nninjoon.userservice.vo.Greeting;
import com.nninjoon.userservice.vo.RequestUser;
import com.nninjoon.userservice.vo.ResponseUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UsersController {

	private final Greeting greeting;
	private final Environment env;
	private final UserService userService;

	@GetMapping("/health_check")
	public String status(){
		return String.format("It's Working in User Service"
			+ ", PORT(local.server.port)=" + env.getProperty("local.server.port")
			+ ", PORT(server.port)=" + env.getProperty("server.port")
			+ ", jwt.issuer=" + env.getProperty("jwt.issuer")
			+ ", jwt.secretKey=" + env.getProperty("jwt.secretKey")
			+ ", token.secret=" + env.getProperty("token.secret"));
	}

	@GetMapping("/welcome")
	public String welcome(){
		// return env.getProperty("greeting.message");
		return greeting.message();
	}

	@PostMapping("/users")
	public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser request){
		ResponseUser responseUser = userService.createUser(request);

		return ResponseEntity.status(CREATED).body(responseUser);
	}

	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers(){
		Iterable<UserEntity> userList = userService.getAllUsers();

		List<ResponseUser> result = new ArrayList<>();
		userList.forEach(user ->
			result.add(new ModelMapper().map(user, ResponseUser.class))
		);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable String userId){
		UserDto userDto = userService.getUserById(userId);
		ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);

		return ResponseEntity.ok(responseUser);
	}
}
