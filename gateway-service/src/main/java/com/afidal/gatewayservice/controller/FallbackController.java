package com.afidal.gatewayservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @CircuitBreaker(name = "moviesService")
    @GetMapping("/movies")
    public ResponseEntity<String> moviesFallback() {
        return new ResponseEntity<>("Movie Service is currently unavailable. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CircuitBreaker(name = "catalogService")
    @GetMapping("/catalogs")
    public ResponseEntity<String> catalogsFallback() {
        return new ResponseEntity<>("Catalog Service is currently unavailable. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CircuitBreaker(name = "seriesService")
    @GetMapping("/series")
    public ResponseEntity<String> seriesFallback() {
        return new ResponseEntity<>("Series Service is currently unavailable. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}