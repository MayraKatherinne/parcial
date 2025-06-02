package com.kathar.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kathar.app.models.Logistica;
import com.kathar.app.repositories.UsuarioLogisticaRepository;

@Service

public class UsuarioLogisticaService {


	    @Autowired
	    private UsuarioLogisticaRepository logisticaRepository;

	    public List<Logistica> obtenerTodos() {
	        return logisticaRepository.findAll();
	    }

	    public void guardar(Logistica logistica) {
	        logisticaRepository.save(logistica);
	    }

	    public void eliminarPorId(String id) {
	        logisticaRepository.deleteById(id);
	    }

	    public Logistica obtenerPorId(String id) {
	        return logisticaRepository.findById(id).orElse(null);
	    }
	}

