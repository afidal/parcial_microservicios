package com.afidal.catalogservice.service.impl;

import com.afidal.catalogservice.model.Series;
import com.afidal.catalogservice.model.dto.SeriesDTO;
import com.afidal.catalogservice.service.SeriesFeignClient;
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
public class SeriesService {

    private Logger LOG = LoggerFactory.getLogger(SeriesService.class);

    private final SeriesFeignClient seriesFeignClient;

    @Autowired
    public SeriesService(SeriesFeignClient seriesFeignClient) {

        this.seriesFeignClient = seriesFeignClient;
    }

    //@CircuitBreaker(name = "series", fallbackMethod = "seriesFallbackMethod")
    //@Retry(name = "series")
    public ResponseEntity<List<SeriesDTO>> getSeriesByGenre(String genre) {
        LOG.info("Making a request to Series Service...");
        return seriesFeignClient.getSeriesByGenre(genre);
    }

    @CircuitBreaker(name = "series", fallbackMethod = "seriesFallbackMethod")
    @Retry(name = "series")
    public ResponseEntity<List<SeriesDTO>> getSeriesByGenre(String genre,Boolean throwErrorSeries) {
        LOG.info("Making a request to Series Service...");
        return seriesFeignClient.getSeriesByGenre(genre,throwErrorSeries);
    }

    private ResponseEntity<List<SeriesDTO>> seriesFallbackMethod(CallNotPermittedException exception) {
        LOG.info("There was a problem with the method findSeriesByGenre. Circuitbreaker has activated.");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
