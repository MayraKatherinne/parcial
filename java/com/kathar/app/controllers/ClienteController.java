package com.kathar.app.controllers;

import com.kathar.app.models.Cliente;
import com.kathar.app.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/cliente")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;

	// Mostrar lista de clientes
	@GetMapping("/listar")
	public String listarClientes(Model model) {
	    model.addAttribute("clientes", clienteRepository.findAll());
	    return "cliente/listar";
	}

	// Mostrar formulario para crear nuevo cliente
	@GetMapping("/nuevo")
	public String mostrarFormularioNuevo(Model model) {
	    model.addAttribute("cliente", new Cliente());
	    return "cliente/nuevo";
	}

	// Guardar cliente nuevo
	@PostMapping("/guardar")
	public String guardarCliente(@ModelAttribute Cliente cliente) {
	    clienteRepository.save(cliente);
	    return "redirect:/admin/cliente/listar";
	}

	// Mostrar formulario para editar cliente
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable String id, Model model) {
	    Optional<Cliente> clienteOpt = clienteRepository.findById(id);
	    if (clienteOpt.isPresent()) {
	        model.addAttribute("cliente", clienteOpt.get());
	        return "cliente/cliente";
	    } else {
	        return "redirect:/admin/cliente/listar";
	    }
	}

	// Actualizar cliente existente
	@PostMapping("/actualizar")
	public String actualizarCliente(@ModelAttribute Cliente cliente) {
	    clienteRepository.save(cliente);
	    return "redirect:/admin/cliente/listar";
	}

	// Eliminar cliente
	@GetMapping("/eliminar/{id}")
	public String eliminarCliente(@PathVariable String id) {
	    clienteRepository.deleteById(id);
	    return "redirect:/admin/cliente/listar";
	}

}