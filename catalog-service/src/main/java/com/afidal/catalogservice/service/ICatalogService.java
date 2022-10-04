package com.afidal.catalogservice.service;

import com.afidal.catalogservice.model.dto.CatalogDTO;

public interface ICatalogService {
    CatalogDTO getCatalogByGenreThroughFeign(String genre);
    CatalogDTO getCatalogByGenreThroughFeign(String genre, Boolean throwErrorMovie, Boolean throwErrorSeries);
    CatalogDTO saveCatalogDTO(CatalogDTO catalogDTO);
    CatalogDTO getCatalogByGenreFromCatalogDB(String genre);
}
