package com.kathar.app.repositories;

import com.kathar.app.models.Logistica;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogisticaRepository extends MongoRepository<Logistica, String> {
}
