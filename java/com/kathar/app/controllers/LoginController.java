package com.kathar.app.controllers;

import com.kathar.app.models.Usuario;
import com.kathar.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model,
                             HttpSession session) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            if (usuario.getPassword().equals(password)) {
                session.setAttribute("usuarioId", usuario.getId());
                session.setAttribute("usuarioRol", usuario.getRol());
                session.setAttribute("usuarioNombre", usuario.getNombre());

                switch (usuario.getRol()) {
                    case "admin":
                        return "redirect:/admin";
                    case "logistica":
                        return "redirect:/logistica";
                    case "inventario":
                        return "redirect:/inventario";
                    case "publicidad":
                        return "redirect:/publicidad";
                    case "usuarioMercadeo":
                        return "redirect:/mercadeo";
                    case "cliente":
                        return "redirect:/";  // Redirigir al index general
                    default:
                        model.addAttribute("error", "Rol no reconocido.");
                        return "login";
                }
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "login";
    }

    @PostMapping("/registro")
    public String registroPost(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("nombre") String nombre,
                                HttpSession session,
                                Model model) {

        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "registro";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setRol("cliente");

        usuarioRepository.save(nuevoUsuario);
        session.setAttribute("usuarioId", nuevoUsuario.getId());
        session.setAttribute("usuarioRol", nuevoUsuario.getRol());
        session.setAttribute("usuarioNombre", nuevoUsuario.getNombre());

        return "redirect:/";  // Redirigir al index general
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
