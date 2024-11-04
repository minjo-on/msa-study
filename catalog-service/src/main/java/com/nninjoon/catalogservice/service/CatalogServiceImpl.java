package com.nninjoon.catalogservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nninjoon.catalogservice.repository.CatalogRepository;
import com.nninjoon.catalogservice.vo.ResponseCatalog;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
	private final CatalogRepository catalogRepository;

	@Override
	public List<ResponseCatalog> getAllCatalogs() {
		return catalogRepository.findAll().stream()
			.map(ResponseCatalog::from)
			.toList();
	}
}
