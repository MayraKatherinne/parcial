package com.kathar.app.services;

import com.kathar.app.models.*;
import com.kathar.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocionPreparadaService {

    @Autowired
    private LocionPreparadaRepository repository;

    public List<LocionPreparada> listarTodas() {
        return repository.findAll();
    }

    public LocionPreparada guardar(LocionPreparada locion) {
        return repository.save(locion);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }

    public LocionPreparada buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }
}
