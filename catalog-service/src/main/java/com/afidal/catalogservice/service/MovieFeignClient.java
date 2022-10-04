package com.afidal.catalogservice.service;


import com.afidal.catalogservice.config.LoadBalancerConfig;
import com.afidal.catalogservice.model.Movie;
import com.afidal.catalogservice.model.dto.MovieDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@FeignClient(name = "movie-service")
@LoadBalancerClient(name = "movie-service", configuration = LoadBalancerConfig.class)
public interface MovieFeignClient {

    @GetMapping("/movies/{genre}")
    ResponseEntity<List<MovieDTO>> getMoviesByGenre(@PathVariable String genre);

    @GetMapping("/movies/withErrors/{genre}")
    ResponseEntity<List<MovieDTO>> getMoviesByGenre(@PathVariable String genre, @RequestParam boolean throwError);

}