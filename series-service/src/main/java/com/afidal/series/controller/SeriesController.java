package com.afidal.series.controller;

import java.util.List;
import com.afidal.series.model.Series;
import com.afidal.series.service.ISeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/series")
public class SeriesController {

    // Para probar el Load Balancer del Gateway al levantar mas de una instancia del Series Service
    @Value("${server.port}")
    private String port;

    private final ISeriesService seriesService;

    public static final Logger LOG = LoggerFactory.getLogger(SeriesController.class);

    @Autowired
    public SeriesController(ISeriesService seriesService) {

            this.seriesService = seriesService;
    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<Series>> getSeriesByGenre(@PathVariable String genre, HttpServletResponse response) {
        response.addHeader("port", port);
        LOG.info("The port is: " + port);
        return ResponseEntity.ok().body(seriesService.findSeriesByGenre(genre));
        }

    @GetMapping("/withErrors/{genre}")
    public ResponseEntity<List<Series>> getSeriesByGenre(@PathVariable String genre, HttpServletResponse response, @RequestParam boolean throwError) {
        response.addHeader("port", port);
        LOG.info("The port is: " + port);
        return ResponseEntity.ok().body(seriesService.findSeriesByGenre(genre, throwError));
    }

        @PostMapping
        public ResponseEntity<Series> saveSeries(@RequestBody Series series) {
            return ResponseEntity.ok().body(seriesService.saveSeries(series));
        }

}
