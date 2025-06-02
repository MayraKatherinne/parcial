package com.kathar.app.controllers;

import com.kathar.app.models.Inventario;
import com.kathar.app.repositories.InventarioRepository;
import com.kathar.app.services.InventarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioService inventarioService;
    
    // Vista del formulario para agregar nuevo inventario
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoInventario(Model model) {
        model.addAttribute("inventario", new Inventario()); // para que funcione th:object
        return "admin/inventario/nuevo"; // debes tener el HTML en templates/admin/inventario/nuevo.html
    }

    // Guardar inventario desde el formulario
    @PostMapping("/guardar")
    public String guardarInventario(@ModelAttribute Inventario inventario) {
        inventarioRepository.save(inventario);
        return "redirect:/admin/inventario/listar"; // redirige a la página de lista
    }
    
    // Listar inventarios
    @GetMapping("/listar")
    public String listarInventarios(Model model) {
        List<Inventario> inventarios = inventarioRepository.findAll();
        model.addAttribute("inventarios", inventarios);
        return "admin/inventario/listar"; // sin .html, sin /static
    }

    // Vista del formulario para editar inventario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarInventario(@PathVariable("id") String id, Model model) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado"));
        model.addAttribute("inventario", inventario);
        return "admin/inventario/editar"; // Debes tener el HTML en templates/admin/inventario/editar.html
    }

    // Actualizar inventario
    @PostMapping("/actualizar")
    public String actualizarInventario(@ModelAttribute Inventario inventario) {
        inventarioRepository.save(inventario); // Guarda los cambios
        return "redirect:/admin/inventario/listar"; // Redirige a la lista después de guardar
    }

    // API para obtener todos (opcional si lo necesitas)
    @ResponseBody
    @GetMapping("/api")
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarInventario(@PathVariable String id) {
        inventarioService.eliminarPorId(id); // Elimina el inventario de MongoDB
        return "redirect:/admin/inventario/listar";
    }
}
