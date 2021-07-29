package com.climainter.dto;

import java.util.List;

import com.climainter.Entidades.Civilizacion;

public class ResponseResumenClima {

	Civilizacion civilizacion;
	int periodosSequiaTotal;
	int periodosLluviaTotal;
	int periodosOptimosTotal;
	List<ResumenClimaPorAnio> listaResumenAnual;

	public Civilizacion getCivilizacion() {
		return civilizacion;
	}

	public void setCivilizacion(Civilizacion civilizacion) {
		this.civilizacion = civilizacion;
	}

	public int getPeriodosSequiaTotal() {
		return periodosSequiaTotal;
	}

	public void setPeriodosSequiaTotal(int periodosSequiaTotal) {
		this.periodosSequiaTotal = periodosSequiaTotal;
	}

	public int getPeriodosLluviaTotal() {
		return periodosLluviaTotal;
	}

	public void setPeriodosLluviaTotal(int periodosLluviaTotal) {
		this.periodosLluviaTotal = periodosLluviaTotal;
	}

	public int getPeriodosOptimosTotal() {
		return periodosOptimosTotal;
	}

	public void setPeriodosOptimosTotal(int periodosOptimosTotal) {
		this.periodosOptimosTotal = periodosOptimosTotal;
	}

	public List<ResumenClimaPorAnio> getListaResumenAnual() {
		return listaResumenAnual;
	}

	public void setListaResumenAnual(List<ResumenClimaPorAnio> listaResumenAnual) {
		this.listaResumenAnual = listaResumenAnual;
	}

}
