package com.afidal.catalogservice.model.dto;

import java.util.Date;
import java.util.List;

public class CatalogDTO {
	private String genre;
	private List<MovieDTO> movies;
	private List<SeriesDTO> series;
	private Date requestTimeStamp;

	public CatalogDTO() {
		//No-args constructor
	}

	public CatalogDTO(String genre, List<MovieDTO> movies, List<SeriesDTO> series) {
		this.genre = genre;
		this.movies = movies;
		this.series = series;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<MovieDTO> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieDTO> movies) {
		this.movies = movies;
	}

	public List<SeriesDTO> getSeries() {
		return series;
	}

	public void setSeries(List<SeriesDTO> series) {
		this.series = series;
	}

	public Date getRequestTimeStamp() {
		return requestTimeStamp;
	}

	public void setRequestTimeStamp(Date requestTimeStamp) {
		this.requestTimeStamp = requestTimeStamp;
	}

	@Override
	public String toString() {
		return "CatalogDTO{" +
				"genre='" + genre + '\'' +
				", movies=" + movies +
				", series=" + series +
				", requestTimeStamp=" + requestTimeStamp +
				'}';
	}
}
