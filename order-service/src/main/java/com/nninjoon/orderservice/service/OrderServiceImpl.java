package com.nninjoon.orderservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nninjoon.orderservice.domain.OrderEntity;
import com.nninjoon.orderservice.dto.OrderDto;
import com.nninjoon.orderservice.repository.OrderRepository;
import com.nninjoon.orderservice.vo.RequestOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;

	@Override
	public OrderEntity createOrder(RequestOrder request, String userId) {
		String orderId = UUID.randomUUID().toString();
		int totalPrice = request.qty() * request.unitPrice();

		OrderEntity orderEntity = OrderEntity.create(request.productId(), request.qty(), request.unitPrice(), totalPrice, userId, orderId);
		orderRepository.save(orderEntity);
		log.info("Order createdAt: {}", orderEntity.getCreatedAt());
		return orderEntity;
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