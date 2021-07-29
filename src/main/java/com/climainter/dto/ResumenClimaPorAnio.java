package com.climainter.dto;

public class ResumenClimaPorAnio {

	int anio;
	int periodosSequia;
	int periodosLLuvia;
	String picoMaximoLluvia;
	int periodosCondicionOptima;

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getPeriodosSequia() {
		return periodosSequia;
	}

	public void setPeriodosSequia(int periodosSequia) {
		this.periodosSequia = periodosSequia;
	}

	public int getPeriodosLLuvia() {
		return periodosLLuvia;
	}

	public void setPeriodosLLuvia(int periodosLLuvia) {
		this.periodosLLuvia = periodosLLuvia;
	}

	public String getPicoMaximoLluvia() {
		return picoMaximoLluvia;
	}

	public void setPicoMaximoLluvia(String picoMaximoLluvia) {
		this.picoMaximoLluvia = picoMaximoLluvia;
	}

	public int getPeriodosCondicionOptima() {
		return periodosCondicionOptima;
	}

	public void setPeriodosCondicionOptima(int periodosCondicionOptima) {
		this.periodosCondicionOptima = periodosCondicionOptima;
	}

}
