package com.kathar.app.controllers;

import com.kathar.app.models.Usuario;
import com.kathar.app.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String panelAdmin() {
        return "admin"; // ya tienes admin.html
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/listar_usuarios";
    }

    @GetMapping("/usuarios/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/crear_usuario";
    }

    @PostMapping("/usuarios/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "admin/crear_usuario";
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            usuario.setPassword("123456");
        }
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable String id, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            model.addAttribute("usuario", optionalUsuario.get());
            return "admin/editar_usuario";
        } else {
            return "redirect:/admin/usuarios";
        }
    }

    @PostMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable String id, @ModelAttribute Usuario usuario) {
        usuario.setId(id);
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
