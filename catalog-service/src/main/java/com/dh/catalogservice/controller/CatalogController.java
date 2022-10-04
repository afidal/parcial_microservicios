package com.dh.catalogservice.controller;

import com.dh.catalogservice.service.impl.CatalogService;
import com.dh.catalogservice.model.CatalogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

	@Value("${server.port}")
	private String port;

	private CatalogService catalogService;

	@Autowired
	public CatalogController(CatalogService catalogService) {

		this.catalogService = catalogService;
	}

	@GetMapping("/{genre}")
	public ResponseEntity<?> findCatalogByGenre(@PathVariable String genre, HttpServletResponse response) {
		response.addHeader("port", port);
		CatalogDTO catalogDTO = catalogService.findCatalogByGenre(genre);
		return Objects.isNull(catalogDTO)
				? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(catalogDTO, HttpStatus.OK);
	}

}
