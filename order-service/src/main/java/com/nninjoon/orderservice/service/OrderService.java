package com.nninjoon.orderservice.service;

import com.nninjoon.orderservice.domain.OrderEntity;
import com.nninjoon.orderservice.dto.OrderDto;
import com.nninjoon.orderservice.vo.RequestOrder;

public interface OrderService {
	OrderEntity createOrder(RequestOrder request, String userId);
	OrderDto getOrderByOrderId(String orderId);
	Iterable<OrderEntity> getOrdersByUserId(String userId);
}
