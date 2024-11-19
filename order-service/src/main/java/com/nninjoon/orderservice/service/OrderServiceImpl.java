package com.nninjoon.orderservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nninjoon.orderservice.domain.OrderEntity;
import com.nninjoon.orderservice.dto.OrderDto;
import com.nninjoon.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;

	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		String orderId = UUID.randomUUID().toString();
		int totalPrice = orderDto.qty() * orderDto.unitPrice();

		OrderEntity orderEntity = OrderEntity.create(orderDto.productId(), orderDto.qty(), orderDto.unitPrice(), totalPrice, orderDto.userId(), orderId);
		orderRepository.save(orderEntity);
		log.info("Order createdAt: {}", orderEntity.getCreatedAt());
		return OrderDto.from(orderEntity);
	}

	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
		OrderDto orderDto = OrderDto.from(orderEntity);

		return orderDto;
	}

	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}
}