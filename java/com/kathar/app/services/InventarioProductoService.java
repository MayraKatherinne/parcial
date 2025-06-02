package com.kathar.app.services;

import com.kathar.app.models.InventarioProducto;
import com.kathar.app.repositories.InventarioProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioProductoService {

    @Autowired
    private InventarioProductoRepository repository;

    public List<InventarioProducto> listar() {
        return repository.findAll();
    }

    public List<InventarioProducto> obtenerTodos() {
        return repository.findAll();
    }

    public void guardar(InventarioProducto producto) {
        repository.save(producto);
    }

    public Optional<InventarioProducto> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public Optional<InventarioProducto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return Optional.empty();
        }
        return repository.findByNombreIgnoreCase(nombre.trim());
    }

    public InventarioProducto obtenerPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        return repository.findByNombreIgnoreCase(nombre.trim()).orElse(null);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
