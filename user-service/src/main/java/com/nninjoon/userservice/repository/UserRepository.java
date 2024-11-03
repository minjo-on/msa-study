package com.nninjoon.userservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.nninjoon.userservice.domain.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUserId(String userId);

	UserEntity findByEmail(String username);
}
