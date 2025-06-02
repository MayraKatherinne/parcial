package com.kathar.app.controllers;

import com.kathar.app.models.Publicidad;
import com.kathar.app.models.Producto;
import com.kathar.app.models.InventarioProducto;
import com.kathar.app.services.PublicidadService;
import com.kathar.app.services.ProductoService;
import com.kathar.app.services.InventarioProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private PublicidadService publicidadService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private InventarioProductoService inventarioProductoService;

    @GetMapping("/")
    public String index(Model model) {
        List<Publicidad> publicidades = publicidadService.obtenerTodas();
        List<Producto> productos = productoService.listar();
        List<InventarioProducto> inventarios = inventarioProductoService.obtenerTodos();

        // Crear un mapa para buscar stock por nombre de producto
        Map<String, Integer> stockMap = new HashMap<>();
        for (InventarioProducto inv : inventarios) {
            stockMap.put(inv.getNombre(), inv.getCantidad());
        }

        model.addAttribute("publicidades", publicidades);
        model.addAttribute("productos", productos);
        model.addAttribute("stockMap", stockMap);

        return "index";
    }
}
