package com.dh.catalogservice.service;


import com.dh.catalogservice.config.LoadBalancerConfig;
import com.dh.catalogservice.model.MovieDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "movie-service")
@LoadBalancerClient(name = "movie-service", configuration = LoadBalancerConfig.class)
public interface MovieFeignClient {


    @GetMapping("/movies/{genre}")
    ResponseEntity<List<MovieDTO>> findMovieByGenre(@PathVariable("genre") String genre);

}
