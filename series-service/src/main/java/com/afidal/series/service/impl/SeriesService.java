package com.afidal.series.service.impl;

import com.afidal.series.model.Series;
import com.afidal.series.repository.SeriesRepository;
import com.afidal.series.service.ISeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService implements ISeriesService {

    @Value("${queue.series.name}")
    private String seriesQueue;

    public static final Logger LOG = LoggerFactory.getLogger(SeriesService.class);

    private final SeriesRepository seriesRepository;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository, RabbitTemplate rabbitTemplate) {
        this.seriesRepository = seriesRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<Series> findSeriesByGenre(String genre) {
        LOG.info("Fetching all " + genre + " series...");
        List<Series> series = seriesRepository.findAllByGenre(genre);
        if (series.isEmpty()) {
            LOG.info("There are no series of the genre " + genre + ".");
        } else {
            LOG.info("Successfully fetched all series of " + genre + " genre.");
        }
        return series;
    }

    @Override
    public List<Series> findSeriesByGenre(String genre, Boolean throwError) {
        LOG.info("Fetching all " + genre + " series...");
        if (throwError) {
            LOG.error("An unknown error occurred while fetching the series. Please try again later.");
            throw new RuntimeException();
        }
        List<Series> series = seriesRepository.findAllByGenre(genre);
        if (series.isEmpty()) {
            LOG.info("There are no series of the genre " + genre + ".");
        } else {
            LOG.info("Successfully fetched all series of " + genre + " genre.");
        }
        return series;
    }

    @Override
    public Series saveSeries(Series series) {
        seriesRepository.save(series);
        LOG.info("The series has been added to the Series database.");
        rabbitTemplate.convertAndSend(seriesQueue, series);
        LOG.info("The series has been sent to the seriesQueue.");
        return series;
    }


}
