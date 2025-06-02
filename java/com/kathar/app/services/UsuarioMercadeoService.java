package com.kathar.app.services;

import com.kathar.app.models.UsuarioMercadeo;
import com.kathar.app.repositories.UsuarioMercadeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioMercadeoService {

    @Autowired
    private UsuarioMercadeoRepository repository;

    // Crear un nuevo usuario
    public void crearUsuario(UsuarioMercadeo usuario) {
        repository.save(usuario);
    }

    // Listar todos los usuarios
    public List<UsuarioMercadeo> listarUsuarios() {
        return repository.findAll();
    }

    // Eliminar un usuario por su ID
    public void eliminarUsuario(String id) {
        repository.deleteById(id);
    }

    // Obtener un usuario por su ID
    public Optional<UsuarioMercadeo> obtenerUsuarioPorId(String id) {
        return repository.findById(id);
    }

    // Actualizar un usuario existente
    public void editarUsuario(UsuarioMercadeo usuario) {
        repository.save(usuario);
    }

    // Autenticación (correo y contraseña)
    public Optional<UsuarioMercadeo> autenticar(String correo, String contraseña) {
        return repository.findByCorreoAndContraseña(correo, contraseña);
    }
}
