package com.climainter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.climainter.dto.ResponseResumenClima;
import com.climainter.service.IClimaService;

@RestController
@RequestMapping("/clima")
public class ClimaRest {

	@Autowired
	private IClimaService climaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/periodosClima/{anios}")
	public ResponseEntity<List<ResponseResumenClima>> consultarPeriodosClima(@PathVariable("anios") int anios) {

		return new ResponseEntity<List<ResponseResumenClima>>(climaService.consultarResumenClima(anios), HttpStatus.OK);
	}
}
