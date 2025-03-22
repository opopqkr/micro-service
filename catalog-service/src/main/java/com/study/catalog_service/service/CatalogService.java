package com.study.catalog_service.service;

import com.study.catalog_service.entity.CatalogEntity;

public interface CatalogService {

    Iterable<CatalogEntity> getAllCatalogs();
}
