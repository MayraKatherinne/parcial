package com.kathar.app.controllers;

import com.kathar.app.models.LocionPreparada;
import com.kathar.app.services.LocionPreparadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lociones")
public class VistaLocionesController {

    @Autowired
    private LocionPreparadaService service;

    // Página principal que lista las lociones
    @GetMapping
    public String listarLociones(Model model) {
        model.addAttribute("lociones", service.listarTodas());
        return "lociones";  // Nombre de la vista Thymeleaf
    }

    // Formulario para crear una nueva loción
    @GetMapping("/nueva")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("locion", new LocionPreparada());
        return "form-locion";  // Nombre del archivo HTML para el formulario
    }

    // Guardar la loción creada
    @PostMapping("/guardar")
    public String guardarLocion(@ModelAttribute LocionPreparada locion) {
        service.guardar(locion);  // Guarda la nueva loción en la base de datos
        return "redirect:/lociones";  // Redirige a la lista de lociones
    }

    // Mostrar el formulario para editar una loción
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable String id, Model model) {
        LocionPreparada locion = service.buscarPorId(id);
        if (locion != null) {
            model.addAttribute("locion", locion);
            return "form-locion";  // Nombre del archivo HTML para el formulario
        }
        return "redirect:/lociones";  // Si no se encuentra la loción, redirige a la lista
    }

    // Actualizar los datos de la loción
    @PostMapping("/actualizar/{id}")
    public String actualizarLocion(@PathVariable String id, @ModelAttribute LocionPreparada nuevaLocion) {
        LocionPreparada locion = service.buscarPorId(id);
        if (locion != null) {
            locion.setNombre(nuevaLocion.getNombre());
            locion.setCantidad(nuevaLocion.getCantidad());
            service.guardar(locion);  // Guarda la loción actualizada
        }
        return "redirect:/lociones";  // Redirige a la lista de lociones
    }

    // Eliminar una loción
    @GetMapping("/eliminar/{id}")
    public String eliminarLocion(@PathVariable String id) {
        service.eliminar(id);
        return "redirect:/lociones";  // Redirige a la lista de lociones
    }
}
