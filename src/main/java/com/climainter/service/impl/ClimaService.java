package com.climainter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.climainter.Entidades.Civilizacion;
import com.climainter.Entidades.Clima;
import com.climainter.dao.IClimaDao;
import com.climainter.dto.ResponseResumenClima;
import com.climainter.dto.ResumenClimaPorAnio;
import com.climainter.service.ICivilizacionService;
import com.climainter.service.IClimaService;
import com.climainter.util.Util;

@Service
public class ClimaService implements IClimaService {

	@Autowired
	private ICivilizacionService civilizacionService;

	@Autowired
	private IClimaDao climaDao;

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<ResponseResumenClima> consultarResumenClima(int anios) {
		List<ResponseResumenClima> listaResult = new ArrayList<ResponseResumenClima>();

		// Realizar Calculos (sabana de dias por ciclo planetario (360 días))
		calcularClimaDias();

		// Consultar los calculos realizados
		listaResult.add(resumenCivilizacion(anios, 1));
		listaResult.add(resumenCivilizacion(anios, 2));
		listaResult.add(resumenCivilizacion(anios, 3));
		return listaResult;

	}

	/**
	 * Calcula la posicion de cada planeta según la velocidad en grados en la que
	 * orbitan
	 * 
	 * @param dias
	 * @return
	 */
	private List<Clima> calcularPosicionPlanetas(int dias) {
		// listar civilizaciones
		List<Civilizacion> civilizaciones = civilizacionService.listarCivilizaciones();
		List<Clima> listaClima = new ArrayList<Clima>();
		int gradosPorDiasBetasoide = 0;
		int gradosPorDiasFerengi = 0;
		int gradosPorDiasVulcano = 0;
		for (int i = 1; i <= dias; i++) {
			Clima diaClima = new Clima();
			diaClima.setId(i);
			diaClima.setDia(i);

			for (Civilizacion civilizacion : civilizaciones) {
				if (civilizacion.getNombreCivilizacion().equalsIgnoreCase("BETASOIDE")) {
					if (gradosPorDiasBetasoide < 360) {
						gradosPorDiasBetasoide = gradosPorDiasBetasoide + civilizacion.getGradosPorDia();
					} else {
						gradosPorDiasBetasoide = civilizacion.getGradosPorDia();
					}
				}

				if (civilizacion.getNombreCivilizacion().equalsIgnoreCase("FERENGI")) {
					if (gradosPorDiasFerengi < 360) {
						gradosPorDiasFerengi = gradosPorDiasFerengi + civilizacion.getGradosPorDia();

					} else {
						gradosPorDiasFerengi = civilizacion.getGradosPorDia();
					}
				}

				if (civilizacion.getNombreCivilizacion().equalsIgnoreCase("VULCANO")) {
					if (gradosPorDiasVulcano < 360) {
						gradosPorDiasVulcano = gradosPorDiasVulcano + civilizacion.getGradosPorDia();
					} else {
						gradosPorDiasVulcano = civilizacion.getGradosPorDia();
					}
				}

				if (civilizacion.getSentidoTraslacion().equalsIgnoreCase("ANTIHORARIO")) {

					diaClima.setGradosVulcano(360 - gradosPorDiasVulcano);
					if (diaClima.getGradosVulcano() == 0) {
						gradosPorDiasVulcano = 360;
					}
				}

				diaClima.setGradosFerengi(gradosPorDiasFerengi);
				diaClima.setGradosBetasoide(gradosPorDiasBetasoide);
			}

			diaClima.setClima(null);

			listaClima.add(diaClima);

		}
		return listaClima;
	}

	/**
	 * Calcula el clima de cada día según la posicion de los planetas y los valores
	 * del triangulo que generan
	 * 
	 * @return
	 */
	private List<Clima> calcularClimaDias() {
		int dias = 360;
		Double ladoA = Double.valueOf(0);
		Double ladoB = Double.valueOf(0);
		Double ladoC = Double.valueOf(0);
		int epoca = 0;
		String climaActual = "";
		List<Clima> diasClima = calcularPosicionPlanetas(dias);
		for (Clima clima : diasClima) {

			// System.out.println("DIA " + clima.getDia());

			// recorrer los lados internos para hallar los tamaños de los lados del
			// triangulo
			Double distanciaFerengi = consultarDistancia("FERENGI");
			Double distanciaBetasoide = consultarDistancia("BETASOIDE");
			Double distanciaVulcano = consultarDistancia("VULCANO");

			// ferengi-vulcano
			Double ladoInternoA = distanciaFerengi;
			Double ladoInternoB = distanciaVulcano;
			Double anguloInterno = Util.calcularAngulo(clima.getGradosFerengi(), clima.getGradosVulcano());
			// System.out.println("Valor lado: " + Util.hallarLado(ladoInternoA,
			// ladoInternoB, anguloInterno));

			ladoA = Util.hallarLado(ladoInternoA, ladoInternoB, anguloInterno);

			// betasoide-vulcano
			ladoInternoA = distanciaVulcano;
			ladoInternoB = distanciaBetasoide;
			anguloInterno = Util.calcularAngulo(clima.getGradosBetasoide(), clima.getGradosVulcano());
			// System.out.println("Valor lado: " + Util.hallarLado(ladoInternoA,
			// ladoInternoB, anguloInterno));

			ladoB = Util.hallarLado(ladoInternoA, ladoInternoB, anguloInterno);

			// betasoide-ferengi
			ladoInternoA = distanciaFerengi;
			ladoInternoB = distanciaBetasoide;
			anguloInterno = Util.calcularAngulo(clima.getGradosFerengi(), clima.getGradosBetasoide());
			// System.out.println("Valor lado: " + Util.hallarLado(ladoInternoA,
			// ladoInternoB, anguloInterno));

			ladoC = Util.hallarLado(ladoInternoA, ladoInternoB, anguloInterno);

			clima.setPerimetro(ladoA + ladoB + ladoC);

			// REGLAS CLIMA------------------------
			// 1. (SEQUIA) Cuando todos los planetas tienen los mismos grados están
			// alineados con el
			// sol
			if (clima.getGradosBetasoide() == clima.getGradosFerengi()
					&& clima.getGradosFerengi() == clima.getGradosVulcano()) {
				clima.setClima("sequia");
			} else if ((clima.getGradosBetasoide() == 360 || clima.getGradosBetasoide() == 0)
					&& (clima.getGradosFerengi() == 360 || clima.getGradosFerengi() == 0)
					&& (clima.getGradosVulcano() == 360 || clima.getGradosVulcano() == 0)) {
				clima.setClima("sequia");
				// 2. Cuando dos planetas tienen los mismos grados y el tercero está en el grado
				// opuesto a los anteriores, están alineados
			} else if ((clima.getGradosBetasoide() == clima.getGradosFerengi()
					&& Math.abs(clima.getGradosVulcano() - clima.getGradosFerengi()) == 180)
					|| (clima.getGradosFerengi() == clima.getGradosVulcano()
							&& Math.abs(clima.getGradosBetasoide() - clima.getGradosFerengi()) == 180)
					|| (clima.getGradosVulcano() == clima.getGradosBetasoide()
							&& Math.abs(clima.getGradosFerengi() - clima.getGradosVulcano()) == 180)) {
				clima.setClima("sequia");
				// 3. (CLIMA ÓPTIMO) Cuando el area de los 3 planetas es 0 y no cumple ninguna
				// de las reglas
				// anteriores están alineados entre si pero no con el sol
			} else if (calcularArea(ladoInternoB, ladoInternoA, ladoInternoB) == 0) {
				clima.setClima("condiciones óptimas de presión y temperatura");
				// 4. cuando los tres planetas se encuentran en distintos cuadrantes
				// (1-90,91-180,181-270,271-360) el sol estará dentro del triangulo
			} else if (validarCuadrantesPlanetas(clima.getGradosBetasoide(), clima.getGradosFerengi(),
					clima.getGradosVulcano())) {
				clima.setClima("lluvia");
			} else {
				clima.setClima("N/A");
			}

			if (!climaActual.equals(clima.getClima())) {
				epoca++;
			}

			climaActual = clima.getClima();
			clima.setEpoca(epoca);
		}

		// insertar
		climaDao.saveAll(diasClima);

		return diasClima;
	}

	/**
	 * devuelve la diastancia que hay entre el sol y un planeta
	 * 
	 * @param civilizacion
	 * @return
	 */
	private Double consultarDistancia(String civilizacion) {
		List<Civilizacion> civilizaciones = civilizacionService.listarCivilizaciones();
		return Double
				.valueOf(civilizaciones.stream().filter(ferengi -> ferengi.getNombreCivilizacion().equals(civilizacion))
						.collect(Collectors.toList()).get(0).getDistanciaAlSol());
	}

	/**
	 * valida si cada planeta está en un cuadrante distinto
	 */
	private boolean validarCuadrantesPlanetas(int angulo1, int angulo2, int angulo3) {
		boolean bRet = false;
		int cuadranteA = 0;
		cuadranteA = (angulo1 >= 1 && angulo1 <= 90) ? 1
				: (angulo1 >= 91 && angulo1 <= 180) ? 2 : (angulo1 >= 181 && angulo1 <= 270) ? 3 : 4;
		int cuadranteB = (angulo2 >= 1 && angulo2 <= 90) ? 1
				: (angulo2 >= 91 && angulo2 <= 180) ? 2 : (angulo2 >= 181 && angulo2 <= 270) ? 3 : 4;
		int cuadranteC = (angulo3 >= 1 && angulo3 <= 90) ? 1
				: (angulo3 >= 91 && angulo3 <= 180) ? 2 : (angulo3 >= 181 && angulo3 <= 270) ? 3 : 4;

		if (cuadranteA != cuadranteB && cuadranteB != cuadranteC && cuadranteA != cuadranteC) {
			bRet = true;
		}
		return bRet;
	}

	/**
	 * Calcula el area del triangulo obtenido entre los tres planetas
	 * 
	 * @param ladoA
	 * @param ladoB
	 * @param ladoC
	 * @return
	 */
	private Double calcularArea(Double ladoA, Double ladoB, Double ladoC) {
		double area = Double.valueOf(0);
		double semiPerimetro = (ladoA + ladoB + ladoC) / 2;
		area = semiPerimetro * (semiPerimetro - ladoA) * (semiPerimetro - ladoB) * (semiPerimetro - ladoC);
		area = Math.sqrt(area);

		return area;
	}

	/**
	 * consulta el resumen del clima para cualquier civilización según los años
	 * enviados
	 * 
	 * @param anios
	 * @return
	 */
	private ResponseResumenClima resumenCivilizacion(int anios, long idCivilizacion) {
		ResponseResumenClima resumenCivilizacion = new ResponseResumenClima();
		int diaInicio = 0;
		int diaFin = 0;
		List<ResumenClimaPorAnio> listaResumen = new ArrayList<>();

		resumenCivilizacion.setCivilizacion(civilizacionService.buscarPorId(idCivilizacion));

		diaInicio = 1;
		int contadorCiclo = 1;
		for (int i = 1; i <= anios; i++) {
			ResumenClimaPorAnio resumenAnio = new ResumenClimaPorAnio();
			diaFin = resumenCivilizacion.getCivilizacion().getAnioEnDias() * contadorCiclo;
			if (diaFin > 360) {
				contadorCiclo = 1;
				diaInicio = 1;
				diaFin = resumenCivilizacion.getCivilizacion().getAnioEnDias();
			}
			String sql = devolverConsultaConLimites(diaInicio, diaFin);

			// LLENAR RESUMEN

			Query nativeQuery;

			nativeQuery = em.createNativeQuery(sql);
			List<Object[]> results = nativeQuery.getResultList();

			for (Object[] objects : results) {
				String clima = (String) objects[1];
				BigDecimal periodos = (BigDecimal) objects[0];
				int periodosTotales = periodos.intValue();
				if (clima.equalsIgnoreCase("LLUVIA")) {
					resumenAnio.setPeriodosLLuvia(periodosTotales);
				}
				if (clima.equalsIgnoreCase("SEQUIA")) {
					resumenAnio.setPeriodosSequia(periodosTotales);
				}
				if (clima.equalsIgnoreCase("condiciones óptimas de presión y temperatura")) {
					resumenAnio.setPeriodosCondicionOptima(periodosTotales);
				}
			}

			// calcular el pico de lluvias por la cantidad de días del año según el planeta,
			// si hay por lo menos un ciclo planetario se toma el valor total del ciclo
			String sqlDiaPico = "SELECT DIA FROM CLIMA_TB WHERE PERIMETRO=(SELECT MAX(PERIMETRO) FROM CLIMA_TB WHERE CLIMA='lluvia' AND DIA BETWEEN "
					+ diaInicio + " AND " + diaFin + ") AND CLIMA='lluvia' AND DIA BETWEEN " + diaInicio + " AND "
					+ diaFin;

			// LLENAR PICO LLUVIA (Calcular los años para los planetas que su orbita dura
			// menos de 360)
			nativeQuery = em.createNativeQuery(sqlDiaPico);
			List<BigDecimal> resultados = nativeQuery.getResultList();
			resumenAnio.setPicoMaximoLluvia("");
			for (BigDecimal dia : resultados) {
				double picoMaximoConversion = dia.doubleValue() / resumenCivilizacion.getCivilizacion().getAnioEnDias();
				if (picoMaximoConversion <= 1) {
					resumenAnio.setPicoMaximoLluvia(resumenAnio.getPicoMaximoLluvia() + "," + dia);
				} else {
					double parteDecimal = picoMaximoConversion % 1;
					picoMaximoConversion = resumenCivilizacion.getCivilizacion().getAnioEnDias() * parteDecimal;
					resumenAnio.setPicoMaximoLluvia(
							resumenAnio.getPicoMaximoLluvia() + "," + Math.round(picoMaximoConversion));
				}

			}
			resumenAnio.setPicoMaximoLluvia(resumenAnio.getPicoMaximoLluvia().replaceFirst(",", ""));

			resumenAnio.setAnio(i);
			listaResumen.add(resumenAnio);
			diaInicio = diaFin + 1;
			contadorCiclo++;

		}
		resumenCivilizacion.setListaResumenAnual(listaResumen);

		// Llenar totales
		int lluviaTotal = 0;
		int sequiaTotal = 0;
		int optimoTotal = 0;
		for (ResumenClimaPorAnio resumenClimaPorAnio : listaResumen) {
			lluviaTotal = lluviaTotal + resumenClimaPorAnio.getPeriodosLLuvia();
			sequiaTotal = sequiaTotal + resumenClimaPorAnio.getPeriodosSequia();
			optimoTotal = optimoTotal + resumenClimaPorAnio.getPeriodosCondicionOptima();
		}
		resumenCivilizacion.setPeriodosLluviaTotal(lluviaTotal);
		resumenCivilizacion.setPeriodosSequiaTotal(sequiaTotal);
		resumenCivilizacion.setPeriodosOptimosTotal(optimoTotal);

		return resumenCivilizacion;

	}

	/**
	 * devuelve la consulta con los limites cuando los años son de menos de 360 dias
	 * *
	 * 
	 * @param diaInicio
	 * @param diaFin
	 * @return
	 */
	private String devolverConsultaConLimites(int diaInicio, int diaFin) {

		return "SELECT COUNT(DIA), CLIMA FROM CLIMA_TB WHERE DIA BETWEEN " + diaInicio + " AND " + diaFin
				+ " GROUP BY CLIMA";
	}

}
