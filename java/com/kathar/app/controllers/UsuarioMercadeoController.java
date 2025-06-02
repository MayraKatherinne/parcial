package com.kathar.app.controllers;

import com.kathar.app.models.UsuarioMercadeo;
import com.kathar.app.services.UsuarioMercadeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/mercadeo")
public class UsuarioMercadeoController {

    @Autowired
    private UsuarioMercadeoService service;

    // Mostrar lista de usuarios
    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listarUsuarios());
        return "mercadeo/usuarios-mercadeo"; // Vista con tabla de usuarios
    }

    // Mostrar el formulario para crear un nuevo usuario
    @GetMapping("/crear")
    public String formularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioMercadeo());
        return "mercadeo/crear"; // Vista del formulario de creación
    }

    // Procesar la creación de un nuevo usuario
    @PostMapping("/crear")
    public String crear(@ModelAttribute UsuarioMercadeo usuario) {
        service.crearUsuario(usuario);
        return "redirect:/mercadeo/usuarios"; // Redirige a la lista de usuarios
    }

    // Mostrar el formulario para editar un usuario
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable String id, Model model) {
        Optional<UsuarioMercadeo> usuario = service.obtenerUsuarioPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "mercadeo/editar"; // Vista del formulario de edición
        } else {
            return "redirect:/mercadeo/usuarios"; // Si no se encuentra el usuario, redirige a la lista
        }
    }

    // Procesar la actualización de un usuario
    @PostMapping("/editar")
    public String editar(@ModelAttribute UsuarioMercadeo usuario) {
        service.editarUsuario(usuario); // Llama al servicio para editar el usuario
        return "redirect:/mercadeo/usuarios"; // Redirige a la lista de usuarios
    }

    // Eliminar un usuario
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        service.eliminarUsuario(id); // Elimina el usuario
        return "redirect:/mercadeo/usuarios"; // Redirige de nuevo a la lista de usuarios
    }

    // Página de login
    @GetMapping("/loginmercadeo")
    public String loginForm() {
        return "mercadeo/landing"; // Vista de login
    }

    // Procesar el login
    @PostMapping("/loginmercadeo")
    public String login(@RequestParam String correo, @RequestParam String contraseña) {
        Optional<UsuarioMercadeo> usuario = service.autenticar(correo, contraseña);
        if (usuario.isPresent()) {
            return "redirect:/mercadeo/landing"; // Redirige al landing si las credenciales son correctas
        } else {
            return "redirect:/mercadeo/loginmercadeo?error=true"; // Si no, muestra un mensaje de error
        }
    }

    // Página de aterrizaje (después del login)
    @GetMapping("/landing")
    public String landing() {
        return "mercadeo/landing"; // Página de aterrizaje
    }

    // Cerrar sesión
    @PostMapping("/logout")
    public String logout() {
        return "redirect:/index"; // Redirige al index después de cerrar sesión
    }
}
