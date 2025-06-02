package com.kathar.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.kathar.app.models.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
Optional<Usuario> findByEmail(String email);
}