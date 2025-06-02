package com.kathar.app.services;

import com.kathar.app.models.Publicidad;
import com.kathar.app.repositories.PublicidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PublicidadService {

    @Autowired
    private PublicidadRepository repo;
    
    public List<Publicidad> obtenerTodas() {
        return repo.findAll();
    }
    public List<Publicidad> listar() {
        return repo.findAll();
    }

    public Optional<Publicidad> obtenerPorId(String id) {
        return repo.findById(id);
    }

    public Publicidad guardar(Publicidad pub) {
        return repo.save(pub);
    }

    public void eliminar(String id) {
        repo.deleteById(id);
    }
}
