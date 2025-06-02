package com.kathar.app.controllers;

import com.kathar.app.models.InventarioProducto;
import com.kathar.app.services.InventarioProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioProductoController {

	@Autowired
	private InventarioProductoService service;

	// ✅ Método agregado para redirigir /inventario → /inventario/lista
	@GetMapping
	public String redirigirALista() {
	    return "redirect:/inventario/lista";
	}

	@GetMapping("/lista")
	public String listarInventario(Model model) {
	    model.addAttribute("productos", service.obtenerTodos()); // Corrige el uso del servicio
	    return "inventario/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
	    model.addAttribute("producto", new InventarioProducto());
	    return "inventario/nuevo";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute InventarioProducto producto) {
	    service.guardar(producto);
	    return "redirect:/inventario/lista";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable String id, Model model) {
	    InventarioProducto producto = service.obtenerPorId(id).orElse(null);
	    model.addAttribute("producto", producto);
	    return "inventario/editar";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute InventarioProducto producto) {
	    service.guardar(producto);
	    return "redirect:/inventario/lista";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
	    service.eliminar(id);
	    return "redirect:/inventario/lista";
	}
}
