package com.mariworld.catalogservice.service;

import com.mariworld.catalogservice.jpa.CatalogRepository;
import com.mariworld.catalogservice.vo.CatalogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService{

    private final CatalogRepository catalogRepository;
    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
