package com.afidal.catalogservice.respository;

import com.afidal.catalogservice.model.CatalogRH;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRHRepository extends MongoRepository<CatalogRH, String> {
}
