package com.kathar.app.services;

import com.kathar.app.models.Cliente;
import com.kathar.app.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll(); // Obtiene todos los clientes desde MongoDB
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente); // Guarda o actualiza el cliente en MongoDB
    }

    public void eliminarPorId(String id) {
        clienteRepository.deleteById(id); // Elimina el cliente por su id
    }

    public Cliente obtenerPorId(String id) {
        Optional<Cliente> cliente = clienteRepository.findById(id); // Obtiene el cliente por su ID
        return cliente.orElse(null); // Retorna el cliente si existe, de lo contrario null
    }
}
