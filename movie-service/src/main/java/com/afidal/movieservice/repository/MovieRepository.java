package com.afidal.movieservice.repository;

import com.afidal.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	List<Movie> findAllByGenre(String genre);
}

