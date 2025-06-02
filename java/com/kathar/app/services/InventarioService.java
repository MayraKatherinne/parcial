package com.kathar.app.services;

import com.kathar.app.models.Inventario;
import com.kathar.app.repositories.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Obtiene todos los usuarios en el inventario
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    // Guarda o actualiza un inventario
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    // Elimina un inventario por su ID
    public void eliminarPorId(String id) {
        inventarioRepository.deleteById(id);
    }

    // Obtiene un inventario por su ID
    public Inventario obtenerPorId(String id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);
        return inventario.orElse(null);
    }
}
