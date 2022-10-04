package com.afidal.movieservice.service;

import com.afidal.movieservice.model.Movie;

import java.util.List;

public interface IMovieService {

	List<Movie> findMoviesByGenre(String genre);
	List<Movie> findMoviesByGenre(String genre, Boolean throwError);
	Movie saveMovie(Movie movie);

}