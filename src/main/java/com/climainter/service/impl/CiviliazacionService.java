package com.climainter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climainter.Entidades.Civilizacion;

import com.climainter.dao.ICivilizacionDao;
import com.climainter.service.ICivilizacionService;

@Service
public class CiviliazacionService implements ICivilizacionService {

	@Autowired
	private ICivilizacionDao civilizacionDao;

	@Override
	public List<Civilizacion> listarCivilizaciones() {
		// TODO Auto-generated method stub
		return civilizacionDao.findAll();
	}

	@Override
	public Civilizacion buscarPorId(long id) {
		return civilizacionDao.findById(id).get();
	}

}
