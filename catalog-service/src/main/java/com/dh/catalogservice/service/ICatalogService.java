package com.dh.catalogservice.service;

import com.dh.catalogservice.model.CatalogDTO;

public interface ICatalogService {
    CatalogDTO findCatalogByGenre(String genre);
}
