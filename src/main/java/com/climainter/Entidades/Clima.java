package com.climainter.Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clima_tb")
public class Clima {

	@Id
	private long id;

	@Column(name = "dia", nullable = false)
	private int dia;

	@Column(name = "grados_ferengi", nullable = false)
	private int gradosFerengi;

	@Column(name = "grados_betasoide", nullable = false)
	private int gradosBetasoide;

	@Column(name = "grados_vulcano", nullable = false)
	private int gradosVulcano;

	@Column(name = "clima", length = 100, nullable = false)
	private String clima;

	@Column(name = "epoca", nullable = false)
	private int epoca;

	@Column(name = "perimetro", nullable = false)
	private double perimetro;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGradosFerengi() {
		return gradosFerengi;
	}

	public void setGradosFerengi(int gradosFerengi) {
		this.gradosFerengi = gradosFerengi;
	}

	public int getGradosBetasoide() {
		return gradosBetasoide;
	}

	public void setGradosBetasoide(int gradosBetasoide) {
		this.gradosBetasoide = gradosBetasoide;
	}

	public int getGradosVulcano() {
		return gradosVulcano;
	}

	public void setGradosVulcano(int gradosVulcano) {
		this.gradosVulcano = gradosVulcano;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public int getEpoca() {
		return epoca;
	}

	public void setEpoca(int epoca) {
		this.epoca = epoca;
	}

	public double getPerimetro() {
		return perimetro;
	}

	public void setPerimetro(double perimetro) {
		this.perimetro = perimetro;
	}

}
