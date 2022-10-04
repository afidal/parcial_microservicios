package com.afidal.catalogservice.controller;

import com.afidal.catalogservice.service.ICatalogService;
import com.afidal.catalogservice.model.dto.CatalogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

	@Value("${server.port}")
	private String port;

	public static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);

	private final ICatalogService catalogService;

	@Autowired
	public CatalogController(ICatalogService catalogService) {
		this.catalogService = catalogService;

	}

	@GetMapping("/{genre}")
	public ResponseEntity<CatalogDTO> getCatalogByGenreThroughFeign(@PathVariable String genre, HttpServletResponse response) {
		response.addHeader("port", port);
		LOG.info("The port is: " + port);
		CatalogDTO catalogDTO = catalogService.getCatalogByGenreThroughFeign(genre);
		return Objects.isNull(catalogDTO)
				? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(catalogDTO, HttpStatus.OK);
	}

	@GetMapping("/withErrors/{genre}")
	ResponseEntity<CatalogDTO> getCatalogByGenre(@PathVariable String genre, HttpServletResponse response, @RequestParam boolean throwErrorMovie, Boolean throwErrorSeries) {
		response.addHeader("port", port);
		LOG.info("The port is: " + port);
		CatalogDTO catalogDTO = catalogService.getCatalogByGenreThroughFeign(genre,throwErrorMovie, throwErrorSeries);
		return Objects.isNull(catalogDTO)
				? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(catalogDTO, HttpStatus.OK);
	}

	@GetMapping("/catalog_db/{genre}")
	public ResponseEntity<CatalogDTO> getCatalogByGenreFromCatalogDB(@PathVariable String genre) {
		CatalogDTO catalogDTO = catalogService.getCatalogByGenreFromCatalogDB(genre);
		return Objects.isNull(catalogDTO)
				? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(catalogDTO, HttpStatus.OK);
	}

}
