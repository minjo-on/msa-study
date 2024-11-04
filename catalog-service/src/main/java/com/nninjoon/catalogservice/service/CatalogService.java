package com.nninjoon.catalogservice.service;

import java.util.List;

import com.nninjoon.catalogservice.vo.ResponseCatalog;

public interface CatalogService {
	List<ResponseCatalog> getAllCatalogs();
}
