package com.nninjoon.orderservice.messagequeue;

import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nninjoon.orderservice.dto.Field;
import com.nninjoon.orderservice.dto.KafkaOrderDto;
import com.nninjoon.orderservice.dto.OrderDto;
import com.nninjoon.orderservice.dto.Payload;
import com.nninjoon.orderservice.dto.Schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;

	List<Field> fields = Arrays.asList(new Field("string", true, "order_id"),
		Field.of("string", true, "user_id"),
		Field.of("string", true, "product_id"),
		Field.of("int32", true, "qty"),
		Field.of("int32", true, "unit_price"),
		Field.of("int32", true, "total_price"));

	Schema schema = Schema.builder()
		.type("struct")
		.fields(fields)
		.optional(false)
		.name("orders")
		.build();

	public OrderDto send(String topic, OrderDto orderDto) {
		Payload payload = Payload.builder()
			.order_id(orderDto.orderId())
			.user_id(orderDto.userId())
			.product_id(orderDto.productId())
			.qty(orderDto.qty())
			.unit_price(orderDto.unitPrice())
			.total_price(orderDto.totalPrice())
			.build();

		KafkaOrderDto kafkaOrderDto = KafkaOrderDto.of(schema, payload);

		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(kafkaOrderDto);
		} catch(JsonProcessingException ex) {
			ex.printStackTrace();
		}

		kafkaTemplate.send(topic, jsonInString);
		log.info("Order Producer sent data from the Order microservice: " + kafkaOrderDto);

		return orderDto;
	}
}
