package com.nninjoon.catalogservice.service;

import org.springframework.stereotype.Service;

import com.nninjoon.catalogservice.domain.CatalogEntity;
import com.nninjoon.catalogservice.repository.CatalogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
	private final CatalogRepository catalogRepository;

	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return catalogRepository.findAll();
	}
}
