package com.climainter.service;

import java.util.List;
import com.climainter.dto.ResponseResumenClima;

public interface IClimaService {

	/**
	 * 
	 * @param anios
	 * @return
	 */
	List<ResponseResumenClima> consultarResumenClima(int anios);

}
