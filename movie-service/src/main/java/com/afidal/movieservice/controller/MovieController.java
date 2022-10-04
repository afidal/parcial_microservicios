package com.afidal.movieservice.controller;

import com.afidal.movieservice.model.Movie;
import com.afidal.movieservice.service.IMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

	// Para probar el Load Balancer del Gateway al levantar mas de una instancia del Movie Service
	@Value("${server.port}")
	private String port;

	private final IMovieService movieService;

	public static final Logger LOG = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	public MovieController(IMovieService movieService) {

		this.movieService = movieService;
	}

	@GetMapping("/{genre}")
	public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genre, HttpServletResponse response) {
		response.addHeader("port", port);
		LOG.info("The port is: " + port);
		return ResponseEntity.ok().body(movieService.findMoviesByGenre(genre));
	}

	@GetMapping("/withErrors/{genre}")
	public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genre, HttpServletResponse response, @RequestParam boolean throwError) {
		response.addHeader("port", port);
		LOG.info("The port is: " + port);
		return ResponseEntity.ok().body(movieService.findMoviesByGenre(genre,throwError));
	}

	@PostMapping
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
		return ResponseEntity.ok().body(movieService.saveMovie(movie));
	}


}
