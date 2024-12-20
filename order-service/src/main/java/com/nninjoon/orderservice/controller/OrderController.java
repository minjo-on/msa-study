package com.nninjoon.orderservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nninjoon.orderservice.domain.OrderEntity;
import com.nninjoon.orderservice.dto.OrderDto;
import com.nninjoon.orderservice.messagequeue.KafkaProducer;
import com.nninjoon.orderservice.messagequeue.OrderProducer;
import com.nninjoon.orderservice.service.OrderService;
import com.nninjoon.orderservice.vo.RequestOrder;
import com.nninjoon.orderservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-service")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
	private final Environment env;
	private final OrderService orderService;
	private final KafkaProducer kafkaProducer;
	private final OrderProducer orderProducer;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in Order Service on LOCAL PORT %s (SERVER PORT %s)",
			env.getProperty("local.server.port"),
			env.getProperty("server.port"));
	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
		@RequestBody RequestOrder request) {

		log.info("Before add orders data");
		/* jpa */
		// OrderEntity orderEntity = orderService.createOrder(request, userId);
		String orderId = UUID.randomUUID().toString();
		OrderDto orderDto = OrderDto.of(request.productId(), request.qty(), request.unitPrice(), orderId, userId);

		/* kafka */
		/* send this order to the kafka */
		kafkaProducer.send("example-catalog-topic", orderDto);
		orderProducer.send("orders", orderDto);

		log.info("After added orders data");
		ResponseOrder responseOrder = ResponseOrder.of(request.productId(), request.qty(), request.unitPrice(), orderId);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception {
		log.info("Before retrieve orders data");
		Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

		List<ResponseOrder> result = new ArrayList<>();
		orderList.forEach(order -> {
			result.add(ResponseOrder.from(order));
		});

		log.info("Add retrieved orders data");

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
