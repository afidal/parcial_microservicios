package com.afidal.catalogservice.respository;

import com.afidal.catalogservice.model.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends MongoRepository<Catalog, String> {
    Catalog findByGenre(String genre);
}
