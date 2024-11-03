package com.nninjoon.catalogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nninjoon.catalogservice.domain.CatalogEntity;
import com.nninjoon.catalogservice.service.CatalogService;
import com.nninjoon.catalogservice.vo.ResponseCatalog;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
	private final CatalogService catalogService;

	@GetMapping("/health-check")
	public String healthCheck(HttpServletRequest request) {
		return String.format("It's Working in Catalog Service on PORT %S", request.getServerPort());
	}

	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getAllCatalogs() {
		Iterable<CatalogEntity> catalogEntities = catalogService.getAllCatalogs();

		List<ResponseCatalog> result = new ArrayList<>();
		catalogEntities.forEach(catalogEntity ->
			result.add(new ModelMapper().map(catalogEntity, ResponseCatalog.class))
		);

		return ResponseEntity.ok(result);
	}
}
