package com.kathar.app.repositories;

import com.kathar.app.models.Publicidad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublicidadRepository extends MongoRepository<Publicidad, String> {
}
