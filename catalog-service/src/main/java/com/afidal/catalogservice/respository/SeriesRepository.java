package com.afidal.catalogservice.respository;

import com.afidal.catalogservice.model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends MongoRepository<Series,String> {
    List<Series> findAllByGenre(String genre);
}
