package com.afidal.movieservice.service.impl;

import com.afidal.movieservice.repository.MovieRepository;
import com.afidal.movieservice.model.Movie;
import com.afidal.movieservice.service.IMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService {

	@Value("${queue.movie.name}")
	private String movieQueue;

	public static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

	private final MovieRepository movieRepository;

	private final RabbitTemplate rabbitTemplate;


	@Autowired
	public MovieService(MovieRepository movieRepository, RabbitTemplate rabbitTemplate) {

		this.movieRepository = movieRepository;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public List<Movie> findMoviesByGenre(String genre) {
		LOG.info("Fetching all " + genre + " movies...");
		List<Movie> movies = movieRepository.findAllByGenre(genre);
		if (movies.isEmpty()) {
			LOG.info("There are no movies of the genre " + genre + ".");
		} else {
		LOG.info("Successfully fetched all movies of " + genre +  " genre."); }
		return movies;
	}

	@Override
	public List<Movie> findMoviesByGenre(String genre, Boolean throwError) {
		LOG.info("Fetching all " + genre + " movies...");
		if (throwError) {
			LOG.error("An unknown error occurred while fetching the movies. Please try again later.");
			throw new RuntimeException();
		}
		List<Movie> movies = movieRepository.findAllByGenre(genre);
		if (movies.isEmpty()) {
			LOG.info("There are no movies of the genre " + genre + ".");
		} else {
			LOG.info("Successfully fetched all movies of " + genre +  " genre."); }
		return movies;
	}


	@Override
	public Movie saveMovie(Movie movie) {
		movieRepository.save(movie);
		LOG.info("The movie " + movie.getName() + " has been added to the Movies database.");
		rabbitTemplate.convertAndSend(movieQueue, movie);
		LOG.info("The movie " + movie.getName() + " has been sent to the movieQueue.");
		return movie;
	}

}
