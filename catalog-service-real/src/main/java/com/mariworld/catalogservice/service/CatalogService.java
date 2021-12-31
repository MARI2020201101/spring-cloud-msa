package com.mariworld.catalogservice.service;

import com.mariworld.catalogservice.vo.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
