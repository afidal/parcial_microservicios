package com.dh.movieservice.controller;

import com.dh.movieservice.service.IMovieService;
import com.dh.movieservice.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Value("${server.port}")
	private String port;

	private IMovieService movieService;

	@Autowired
	public MovieController(IMovieService movieService) {

		this.movieService = movieService;
	}

	@GetMapping("/{genre}")
	public ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre, HttpServletResponse response) {
		response.addHeader("port", port);
		System.out.println("The port is: " + port);

		List<Movie> movies = movieService.getListByGenre(genre);
		return movies.isEmpty()
				? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(movies, HttpStatus.OK);
	}

/*	@PostMapping
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
		return ResponseEntity.ok().body(movieService.save(movie));
	}*/

}
