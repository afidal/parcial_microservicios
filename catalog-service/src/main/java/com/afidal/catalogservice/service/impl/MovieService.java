package com.afidal.catalogservice.service.impl;

import com.afidal.catalogservice.model.Movie;
import com.afidal.catalogservice.model.dto.MovieDTO;
import com.afidal.catalogservice.service.MovieFeignClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final MovieFeignClient movieFeignClient;

    @Autowired
    public MovieService(MovieFeignClient movieFeignClient) {

        this.movieFeignClient = movieFeignClient;
    }

    //@CircuitBreaker(name = "movies", fallbackMethod = "moviesFallbackMethod")
    //@Retry(name = "movies")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(String genre) {
        LOG.info("Making a request to Movie Service...");
        return movieFeignClient.getMoviesByGenre(genre);
    }

    @CircuitBreaker(name = "movies", fallbackMethod = "moviesFallbackMethod")
    @Retry(name = "movies")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(String genre, Boolean throwErrorMovie) {
        LOG.info("Making a request to Movie Service...");
        return movieFeignClient.getMoviesByGenre(genre, throwErrorMovie);
    }

    private ResponseEntity<List<MovieDTO>> moviesFallbackMethod(CallNotPermittedException exception) {
        LOG.info("There was a problem with the method findMoviesByGenre. Circuitbreaker has activated.");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }


}
