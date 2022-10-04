package com.afidal.catalogservice.model;

import java.util.Date;
import java.util.List;

public class Catalog {

    private String id;
    private String genre;
    private List<Movie> movies;
    private List<Series> series;
    private Date requestTimeStamp;

    public Catalog() {
        // No-args constructor
    }

    public Catalog(String id, String genre, List<Movie> movies, List<Series> series) {
        this.id = id;
        this.genre = genre;
        this.movies = movies;
        this.series = series;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
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
        return "Catalog{" +
                "id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", movies=" + movies +
                ", series=" + series +
                ", requestTimeStamp=" + requestTimeStamp +
                '}';
    }
}
