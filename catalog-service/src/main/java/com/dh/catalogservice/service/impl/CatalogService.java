package com.dh.catalogservice.service.impl;
import com.dh.catalogservice.model.CatalogDTO;
import com.dh.catalogservice.model.MovieDTO;
import com.dh.catalogservice.service.ICatalogService;
import com.dh.catalogservice.service.MovieFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService implements ICatalogService {

    private final MovieFeignClient movieFeignClient;

    @Autowired
    public CatalogService(MovieFeignClient movieFeignClient) {

        this.movieFeignClient = movieFeignClient;
    }

    public CatalogDTO findCatalogByGenre(String genre) {
        ResponseEntity<List<MovieDTO>> movieResponse = movieFeignClient.findMovieByGenre(genre);
        if (movieResponse.getStatusCode().is2xxSuccessful())
            return new CatalogDTO(genre, movieResponse.getBody());
        return null;
    }

}