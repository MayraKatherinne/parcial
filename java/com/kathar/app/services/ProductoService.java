package com.kathar.app.services;

import com.kathar.app.models.Producto;
import com.kathar.app.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> listar() {
        return repository.findAll();
    }

    public void guardar(Producto producto) {
        repository.save(producto);
    }

    public Optional<Producto> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public Producto obtenerProductoPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
