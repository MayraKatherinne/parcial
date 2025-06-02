package com.kathar.app.repositories;

import com.kathar.app.models.UsuarioMercadeo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioMercadeoRepository extends MongoRepository<UsuarioMercadeo, String> {

    // Método para autenticar con correo y contraseña
    Optional<UsuarioMercadeo> findByCorreoAndContraseña(String correo, String contraseña);
}