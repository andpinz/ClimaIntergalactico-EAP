package com.climainter.service;

import java.util.List;

import com.climainter.Entidades.Civilizacion;

public interface ICivilizacionService {

	public List<Civilizacion> listarCivilizaciones();

	/**
	 * 
	 * @param id
	 * @return
	 */
	Civilizacion buscarPorId(long id);
}
