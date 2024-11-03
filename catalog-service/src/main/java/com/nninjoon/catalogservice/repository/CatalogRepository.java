package com.nninjoon.catalogservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nninjoon.catalogservice.domain.CatalogEntity;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
	Optional<CatalogEntity> findByProductId(String productId);
}
