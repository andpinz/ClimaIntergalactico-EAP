package com.climainter.util;

public class Util {
	public static Double hallarLado(Double ladoA, Double ladoB, Double anguloConocido) {
		// Teroema del coseno
		Double resultado = 0.0;
		// convertir a radianes
		double radianes = Math.toRadians(anguloConocido);
		resultado = Math.pow(ladoA, 2) + Math.pow(ladoB, 2) - 2 * ladoA * ladoB * Math.cos(radianes);
		resultado = Math.sqrt(resultado);
		return Math.round(resultado * 100.0) / 100.0;
	}

	public static Double calcularAngulo(int gradosA, int gradosB) {
		Double resultado = Double.valueOf(0);
		if (gradosA >= gradosB) {
			resultado = Double.valueOf((360 - gradosA) + gradosB);
		} else {
			resultado = Double.valueOf((360 - gradosB) + gradosA);
		}
		System.out.println(resultado);
		return resultado;

	}

}
