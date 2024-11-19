package com.nninjoon.catalogservice.messagequeue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nninjoon.catalogservice.domain.CatalogEntity;
import com.nninjoon.catalogservice.repository.CatalogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private final CatalogRepository catalogRepository;

	@Transactional
	@KafkaListener(topics = "example-catalog-topic")
	public void updateQty(String kafkaMessage) {
		log.info("Kafka Message: -> " + kafkaMessage);

		Map<Object, Object> map = parseKafkaMessage(kafkaMessage);
		validateKafkaMessage(map);

		CatalogEntity entity = catalogRepository.findByProductId((String)map.get("productId"))
			.orElseThrow(() -> new RuntimeException("Catalog not found"));

		entity.updateStock((Integer)map.get("qty"));
	}

	private Map<Object, Object> parseKafkaMessage(String kafkaMessage) {
		try {
			return new ObjectMapper().readValue(kafkaMessage, new TypeReference<HashMap<Object, Object>>() {});
		} catch (JsonProcessingException e) {
			log.error("Failed to parse Kafka message: {}", kafkaMessage, e);
			throw new RuntimeException("Invalid Kafka message format", e);
		}
	}

	private void validateKafkaMessage(Map<Object, Object> map) {
		if (!map.containsKey("productId") || !map.containsKey("qty")) {
			throw new IllegalArgumentException("Missing required fields in Kafka message: " + map);
		}
		if (!(map.get("productId") instanceof String) || !(map.get("qty") instanceof Integer)) {
			throw new IllegalArgumentException("Invalid field types in Kafka message: " + map);
		}
	}

}
