package com.afidal.catalogservice.service;

import com.afidal.catalogservice.config.LoadBalancerConfig;
import com.afidal.catalogservice.model.dto.SeriesDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@FeignClient(name = "series-service")
@LoadBalancerClient(name = "series-service", configuration = LoadBalancerConfig.class)
public interface SeriesFeignClient {

    @GetMapping("/series/{genre}")
    ResponseEntity<List<SeriesDTO>> getSeriesByGenre(@PathVariable String genre);

    @GetMapping("/series/withErrors/{genre}")
    ResponseEntity<List<SeriesDTO>> getSeriesByGenre(@PathVariable String genre, @RequestParam boolean throwError);

}