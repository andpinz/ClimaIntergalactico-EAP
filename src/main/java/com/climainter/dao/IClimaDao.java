package com.climainter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.climainter.Entidades.Clima;

public interface IClimaDao extends JpaRepository<Clima,Integer>{
}
