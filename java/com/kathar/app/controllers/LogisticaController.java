package com.kathar.app.controllers;

import com.kathar.app.models.Producto;
import com.kathar.app.models.InventarioProducto;
import com.kathar.app.services.ProductoService;
import com.kathar.app.services.InventarioProductoService;
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
@RequestMapping("/logistica")
public class LogisticaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private InventarioProductoService inventarioService;

    private final Path uploadDir = Paths.get("uploads");

    @GetMapping("")
    public String verProductos(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "logistica/index";
    }

    @GetMapping("/listar")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "logistica/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("inventario", inventarioService.listar()); // necesario para el <select>
        return "logistica/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam("file") MultipartFile file,
                                  Model model) {
        Optional<InventarioProducto> inventarioOpt = inventarioService.buscarPorNombre(producto.getNombre().trim());

        if (inventarioOpt.isEmpty()) {
            model.addAttribute("error", "El producto con nombre '" + producto.getNombre() + "' no existe en el inventario.");
            model.addAttribute("producto", producto);
            model.addAttribute("inventario", inventarioService.listar()); // recargar lista si hay error
            return "logistica/nuevo";
        }

        if (inventarioOpt.get().getCantidad() == 0) {
            model.addAttribute("warning", "Advertencia: este producto tiene 0 unidades en inventario.");
        }

        // Guardar imagen si existe
        if (file != null && !file.isEmpty()) {
            try {
                if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
                String nuevoNombre = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path destino = uploadDir.resolve(nuevoNombre);
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen(nuevoNombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productoService.guardar(producto);
        return "redirect:/logistica";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable String id, Model model) {
        Optional<Producto> productoOpt = productoService.obtenerPorId(id);
        if (productoOpt.isPresent()) {
            model.addAttribute("producto", productoOpt.get());
            return "logistica/editar";
        } else {
            return "redirect:/logistica/listar";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto,
                                     @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
                String nuevoNombre = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path destino = uploadDir.resolve(nuevoNombre);
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen(nuevoNombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            productoService.obtenerPorId(producto.getId()).ifPresent(p -> {
                producto.setImagen(p.getImagen());
            });
        }

        productoService.guardar(producto);
        return "redirect:/logistica/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable String id) {
        productoService.eliminar(id);
        return "redirect:/logistica/listar";
    }
}
