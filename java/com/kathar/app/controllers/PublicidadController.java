package com.kathar.app.controllers;

import com.kathar.app.models.Publicidad;
import com.kathar.app.services.PublicidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/publicidad")
public class PublicidadController {

    @Autowired
    private PublicidadService publicidadService;

    private final Path uploadDir = Paths.get("uploads");

    // Mostrar vista principal
    @GetMapping("")
    public String mostrarIndex(Model model) {
        model.addAttribute("publicidades", publicidadService.listar());
        return "publicidad/index";
    }

    // Listar todas las publicidades (vista alternativa)
    @GetMapping("/listar")
    public String listarPublicidad(Model model) {
        model.addAttribute("publicidades", publicidadService.listar());
        return "publicidad/listar";
    }

    // Mostrar formulario para nueva publicidad
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearPublicidad(Model model) {
        model.addAttribute("publicidad", new Publicidad());
        return "publicidad/nuevo";
    }

    // Guardar nueva publicidad
    @PostMapping("/guardar")
    public String guardarPublicidad(@ModelAttribute Publicidad publicidad,
                                    @RequestParam("file") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String nuevoNombre = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path destino = uploadDir.resolve(nuevoNombre);
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                publicidad.setImagen(nuevoNombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        publicidadService.guardar(publicidad);
        return "redirect:/publicidad";
    }

    // Mostrar formulario para editar publicidad
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPublicidad(@PathVariable String id, Model model) {
        Optional<Publicidad> publicidadOpt = publicidadService.obtenerPorId(id);
        if (publicidadOpt.isPresent()) {
            model.addAttribute("publicidad", publicidadOpt.get());
            return "publicidad/editar";
        } else {
            return "redirect:/publicidad/listar";
        }
    }

    // Actualizar publicidad existente
    @PostMapping("/actualizar")
    public String actualizarPublicidad(@ModelAttribute Publicidad publicidad,
                                       @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String nuevoNombre = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path destino = uploadDir.resolve(nuevoNombre);
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                publicidad.setImagen(nuevoNombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            publicidadService.obtenerPorId(publicidad.getId()).ifPresent(p -> {
                publicidad.setImagen(p.getImagen()); // Mantener imagen anterior si no se sube nueva
            });
        }

        publicidadService.guardar(publicidad);
        return "redirect:/publicidad/listar";
    }

    // Eliminar publicidad
    @GetMapping("/eliminar/{id}")
    public String eliminarPublicidad(@PathVariable String id) {
        publicidadService.eliminar(id);
        return "redirect:/publicidad/listar";
    }
}
