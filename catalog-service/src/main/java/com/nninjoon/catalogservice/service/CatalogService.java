package com.nninjoon.catalogservice.service;

import com.nninjoon.catalogservice.domain.CatalogEntity;

public interface CatalogService {
	Iterable<CatalogEntity> getAllCatalogs();
}
