package com.kathar.app.repositories;

import com.kathar.app.models.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    // Puedes añadir métodos personalizados si es necesario
}
