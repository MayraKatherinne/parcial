package com.kathar.app.repositories;


import com.kathar.app.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocionPreparadaRepository extends MongoRepository<LocionPreparada, String> {
    // puedes agregar métodos personalizados aquí si lo necesitas
}
