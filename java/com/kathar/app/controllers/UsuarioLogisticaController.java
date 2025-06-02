package com.kathar.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kathar.app.models.Logistica;
import com.kathar.app.services.UsuarioLogisticaService;

@Controller
@RequestMapping("/admin/logistica")
@CrossOrigin(origins = "*")
public class UsuarioLogisticaController {
	

	    @Autowired
	    private UsuarioLogisticaService logisticaService;

	    @GetMapping("/nuevo")
	    public String mostrarFormularioNuevo(Model model) {
	        model.addAttribute("logistica", new Logistica());
	        return "admin/logistica/nuevo";
	    }

	    @PostMapping("/guardar")
	    public String guardar(@ModelAttribute Logistica logistica) {
	        logisticaService.guardar(logistica);
	        return "redirect:/admin/logistica/listar";
	    }

	    @GetMapping("/listar")
	    public String listar(Model model) {
	        model.addAttribute("logisticas", logisticaService.obtenerTodos());
	        return "admin/logistica/listar";
	    }

	    @GetMapping("/editar/{id}")
	    public String editar(@PathVariable String id, Model model) {
	        model.addAttribute("logistica", logisticaService.obtenerPorId(id));
	        return "admin/logistica/editar";
	    }

	    @PostMapping("/actualizar")
	    public String actualizar(@ModelAttribute Logistica logistica) {
	        logisticaService.guardar(logistica);
	        return "redirect:/admin/logistica/listar";
	    }

	    @GetMapping("/eliminar/{id}")
	    public String eliminar(@PathVariable String id) {
	        logisticaService.eliminarPorId(id);
	        return "redirect:/admin/logistica/listar";
	    }
	}


