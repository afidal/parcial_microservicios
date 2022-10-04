package com.dh.movieservice.service.impl;

import com.dh.movieservice.model.Movie;
import com.dh.movieservice.repository.MovieRepository;
import com.dh.movieservice.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService {

	private MovieRepository movieRepository;

	@Autowired
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> getListByGenre(String genre) {

		return movieRepository.findAllByGenre(genre);
	}

/*	@Override
	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	}*/

}
