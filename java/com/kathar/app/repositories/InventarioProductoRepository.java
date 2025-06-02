package com.kathar.app.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.kathar.app.models.InventarioProducto;

import java.util.Optional;

public interface InventarioProductoRepository extends MongoRepository<InventarioProducto, String> {
Optional<InventarioProducto> findByNombreIgnoreCase(String nombre);
}

