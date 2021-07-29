package com.climainter.Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "civilizacion_tb")
public class Civilizacion {

	@Id
	private Long id;
	
	@Column(name="nombre",length = 50,nullable = false)
	private String nombreCivilizacion;
	
	@Column(name="anio_en_dias",nullable = false)
	private int anioEnDias;
	
	@Column(name="distancia_al_sol",nullable = false)
	private int distanciaAlSol;
	
	@Column(name="grados_por_dia",nullable = false)
	private int gradosPorDia;
	
	@Column(name="sentido_traslacion",length = 20,nullable = false)
	private String sentidoTraslacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCivilizacion() {
		return nombreCivilizacion;
	}

	public void setNombreCivilizacion(String nombreCivilizacion) {
		this.nombreCivilizacion = nombreCivilizacion;
	}

	public int getAnioEnDias() {
		return anioEnDias;
	}

	public void setAnioEnDias(int anioEnDias) {
		this.anioEnDias = anioEnDias;
	}

	public int getDistanciaAlSol() {
		return distanciaAlSol;
	}

	public void setDistanciaAlSol(int distanciaAlSol) {
		this.distanciaAlSol = distanciaAlSol;
	}

	public int getGradosPorDia() {
		return gradosPorDia;
	}

	public void setGradosPorDia(int gradosPorDia) {
		this.gradosPorDia = gradosPorDia;
	}

	public String getSentidoTraslacion() {
		return sentidoTraslacion;
	}

	public void setSentidoTraslacion(String sentidoTraslacion) {
		this.sentidoTraslacion = sentidoTraslacion;
	}

}
