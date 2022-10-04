package com.dh.movieservice.service;

import com.dh.movieservice.model.Movie;

import java.util.List;

public interface IMovieService {
	List<Movie> getListByGenre(String genre);
	/* Movie save(Movie movie); */
}
