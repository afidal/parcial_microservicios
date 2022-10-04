package com.afidal.series.service;

import com.afidal.series.model.Series;

import java.util.List;

public interface ISeriesService {

    List<Series> findSeriesByGenre(String genre);
    List<Series> findSeriesByGenre(String genre, Boolean throwError);
    Series saveSeries(Series series);








}


