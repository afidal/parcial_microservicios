package com.afidal.catalogservice.service.impl;

import com.afidal.catalogservice.model.CatalogRH;
import com.afidal.catalogservice.model.Movie;
import com.afidal.catalogservice.model.Series;
import com.afidal.catalogservice.model.dto.CatalogDTO;
import com.afidal.catalogservice.model.dto.MovieDTO;
import com.afidal.catalogservice.model.dto.SeriesDTO;
import com.afidal.catalogservice.respository.CatalogRHRepository;
import com.afidal.catalogservice.respository.CatalogRepository;
import com.afidal.catalogservice.respository.MovieRepository;
import com.afidal.catalogservice.respository.SeriesRepository;
import com.afidal.catalogservice.service.ICatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CatalogService implements ICatalogService {

    private final Logger LOG = LoggerFactory.getLogger(CatalogService.class);

    private final SeriesService seriesService;

    private final MovieService movieService;

    private final CatalogRepository catalogRepository;

    private final CatalogRHRepository catalogRHRepository;

    private final MovieRepository movieRepository;

    private final SeriesRepository seriesRepository;

    private final ObjectMapper mapper;

    @Autowired
    public CatalogService(SeriesService seriesService, MovieService movieService, CatalogRepository catalogRepository, CatalogRHRepository catalogRHRepository, MovieRepository movieRepository, SeriesRepository seriesRepository, ObjectMapper mapper) {
        this.seriesService = seriesService;
        this.movieService = movieService;
        this.catalogRepository = catalogRepository;
        this.catalogRHRepository = catalogRHRepository;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.mapper = mapper;
    }

    @Override
    public CatalogDTO getCatalogByGenreThroughFeign(String genre) {
        ResponseEntity<List<MovieDTO>> movies = movieService.getMoviesByGenre(genre);
        List<MovieDTO> moviesDTOList = movies.getBody();
        ResponseEntity<List<SeriesDTO>> series = seriesService.getSeriesByGenre(genre);
        List<SeriesDTO> seriesDTOList = series.getBody();
        CatalogDTO catalogDTO = new CatalogDTO(genre, moviesDTOList, seriesDTOList);
        LOG.info("We are fetching the catalog for genre " + genre +"...");
        saveCatalogDTO(catalogDTO);
        LOG.info("The response of the request and the date and time it was made were successfully recorded.");
        return catalogDTO;
    }

    @Override
    public CatalogDTO getCatalogByGenreThroughFeign(String genre, Boolean throwErrorMovie, Boolean throwErrorSeries) {
        ResponseEntity<List<MovieDTO>> movies = movieService.getMoviesByGenre(genre, throwErrorMovie);
        List<MovieDTO> moviesDTOList = movies.getBody();
        ResponseEntity<List<SeriesDTO>> series = seriesService.getSeriesByGenre(genre, throwErrorSeries);
        List<SeriesDTO> seriesDTOList = series.getBody();
        CatalogDTO catalogDTO = new CatalogDTO(genre, moviesDTOList, seriesDTOList);
        LOG.info("We are fetching the catalog for genre " + genre +"...");
        saveCatalogDTO(catalogDTO);
        LOG.info("The response of the request and the date and time it was made were successfully recorded.");
        return catalogDTO;
    }

    @Override
    public CatalogDTO saveCatalogDTO(CatalogDTO catalogDTO) {
        catalogDTO.setRequestTimeStamp(new Date());
        CatalogRH catalogRH = mapper.convertValue(catalogDTO, CatalogRH.class);
        CatalogRH catalogDB = catalogRHRepository.save(catalogRH);
        return mapper.convertValue(catalogDB, CatalogDTO.class);
    }

    @RabbitListener(queues = "${queue.movie.name}" )
    public void addMovieToCatalogDB (Movie movie) {
        LOG.info("The movie " + movie.getName() + " has been received through RabbitMQ.");
        movieRepository.save(movie);
        LOG.info("The movie " + movie.getName() + " has been added to the database.");
    }

    @RabbitListener(queues = "${queue.series.name}" )
    public void addSeriesToCatalogDB (Series series) {
        LOG.info("The series " + series.getName() + " has been received through RabbitMQ.");
        seriesRepository.save(series);
        LOG.info("The series " + series.getName() + " has been added to the database.");
    }

    @Override
    public CatalogDTO getCatalogByGenreFromCatalogDB(String genre){
        List<Movie> movies = movieRepository.findAllByGenre(genre);
        List<MovieDTO> moviesDTO = new ArrayList<MovieDTO>();
        for (Movie movie : movies) {
            moviesDTO.add(mapper.convertValue(movie, MovieDTO.class));
        }
        List<Series> seriesList = seriesRepository.findAllByGenre(genre);
        List<SeriesDTO> seriesDTO = new ArrayList<SeriesDTO>();
        for (Series series : seriesList) {
            seriesDTO.add(mapper.convertValue(series, SeriesDTO.class));
        }
        return new CatalogDTO(genre, moviesDTO, seriesDTO);
    }

}