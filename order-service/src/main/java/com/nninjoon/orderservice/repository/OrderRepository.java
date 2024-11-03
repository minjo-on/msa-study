package com.nninjoon.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nninjoon.orderservice.domain.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	OrderEntity findByOrderId(String orderId);
	Iterable<OrderEntity> findByUserId(String userId);
}
