package com.kathar.app.controllers;

import com.kathar.app.models.CarritoItem;
import com.kathar.app.models.InventarioProducto;
import com.kathar.app.models.Producto;
import com.kathar.app.services.CarritoService;
import com.kathar.app.services.InventarioProductoService;
import com.kathar.app.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
	@Autowired
	private CarritoService carritoService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private InventarioProductoService inventarioService;

	@PostMapping("/agregar")
	public String agregarAlCarrito(@RequestParam String productoId,
	                               @RequestParam int cantidad,
	                               HttpSession session,
	                               Model model) {

	    String clienteId = (String) session.getAttribute("usuarioId");
	    if (clienteId == null) return "redirect:/login";

	    Producto producto = productoService.obtenerProductoPorId(productoId);

	    if (producto != null && producto.getNombre() != null) {
	        InventarioProducto inventario = inventarioService.obtenerPorNombre(producto.getNombre());

	        if (inventario != null) {
	            int cantidadDisponible = inventario.getCantidad();

	            int cantidadEnCarrito = carritoService
	                    .obtenerCarritoPorCliente(clienteId)
	                    .stream()
	                    .filter(item -> item.getProductoId().equals(productoId))
	                    .mapToInt(CarritoItem::getCantidad)
	                    .sum();

	            if (cantidadEnCarrito + cantidad <= cantidadDisponible) {
	                CarritoItem item = new CarritoItem();
	                item.setClienteId(clienteId);
	                item.setProductoId(producto.getId());
	                item.setNombreProducto(producto.getNombre());
	                item.setImagen(producto.getImagen());
	                item.setPrecio(producto.getPrecio());
	                item.setCantidad(cantidad);
	                carritoService.agregarAlCarrito(item);
	                return "redirect:/";
	            } else {
	                model.addAttribute("error", "Cantidad solicitada supera el inventario disponible.");
	                return "redirect:/?error=inventario";
	            }
	        }
	    }

	    return "redirect:/?error=producto";
	}

	@GetMapping
	public String verCarrito(Model model, HttpSession session) {
	    String clienteId = (String) session.getAttribute("usuarioId");
	    if (clienteId == null) return "redirect:/login";

	    List<CarritoItem> items = carritoService.obtenerCarritoPorCliente(clienteId);
	    double total = items.stream().mapToDouble(i -> i.getCantidad() * i.getPrecio()).sum();

	    model.addAttribute("carrito", items);
	    model.addAttribute("total", total);
	    return "carrito/ver";
	}

	@PostMapping("/actualizar")
	public String actualizarCantidad(@RequestParam String itemId,
	                                 @RequestParam int cantidad,
	                                 HttpSession session) {
	    String clienteId = (String) session.getAttribute("usuarioId");
	    if (clienteId == null) return "redirect:/login";

	    CarritoItem item = carritoService.obtenerPorId(itemId);
	    if (item != null) {
	        Producto producto = productoService.obtenerProductoPorId(item.getProductoId());
	        InventarioProducto inventario = inventarioService.obtenerPorNombre(producto.getNombre());

	        if (inventario != null && cantidad <= inventario.getCantidad()) {
	            carritoService.actualizarCantidad(itemId, cantidad);
	        }
	    }
	    return "redirect:/carrito";
	}

	@GetMapping("/eliminar/{productoId}")
	public String eliminarItem(@PathVariable String productoId, HttpSession session) {
	    String clienteId = (String) session.getAttribute("usuarioId");
	    if (clienteId == null) return "redirect:/login";

	    carritoService.eliminarDelCarrito(clienteId, productoId);
	    return "redirect:/carrito";
	}

}