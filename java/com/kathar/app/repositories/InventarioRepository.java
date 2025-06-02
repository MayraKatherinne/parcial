package com.kathar.app.repositories;

import com.kathar.app.models.Inventario;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventarioRepository extends MongoRepository<Inventario, String> {

	Optional<Inventario> findById(String id);
}
