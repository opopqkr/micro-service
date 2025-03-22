package com.study.catalog_service.repository;

import com.study.catalog_service.entity.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {

    Optional<CatalogEntity> findByProductId(String productId);

}
