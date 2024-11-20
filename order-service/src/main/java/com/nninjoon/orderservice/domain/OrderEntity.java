package com.nninjoon.orderservice.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//    @Column(nullable = false, length = 120, unique = true)
	@Column(nullable = false, length = 120)
	private String productId;
	@Column(nullable = false)
	private Integer qty;
	@Column(nullable = false)
	private Integer unitPrice;
	@Column(nullable = false)
	private Integer totalPrice;

	@Column(nullable = false)
	private String userId;
	@Column(nullable = false, unique = true)
	private String orderId;

	@Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	public static OrderEntity create(String productId, Integer qty, Integer unitPrice, Integer totalPrice, String userId, String orderId) {
		return OrderEntity.builder()
			.productId(productId)
			.qty(qty)
			.unitPrice(unitPrice)
			.totalPrice(totalPrice)
			.userId(userId)
			.orderId(orderId)
			.createdAt(LocalDateTime.now())
			.build();
	}
}