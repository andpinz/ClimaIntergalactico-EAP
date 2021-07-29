package com.climainter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.climainter.Entidades.Civilizacion;

public interface ICivilizacionDao extends JpaRepository<Civilizacion,Long>{

}
