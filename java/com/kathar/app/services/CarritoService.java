package com.kathar.app.services;

import com.kathar.app.models.CarritoItem;
import com.kathar.app.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    // Agrega un producto al carrito (si ya existe, suma cantidades)
    public void agregarAlCarrito(CarritoItem item) {
        CarritoItem existente = carritoRepository.findByClienteIdAndProductoId(item.getClienteId(), item.getProductoId());
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + item.getCantidad());
            carritoRepository.save(existente);
        } else {
            carritoRepository.save(item);
        }
    }

    // Obtiene todos los productos del carrito de un cliente
    public List<CarritoItem> obtenerCarritoPorCliente(String clienteId) {
        return carritoRepository.findByClienteId(clienteId);
    }

    // Actualiza la cantidad de un ítem en el carrito
    public void actualizarCantidad(String id, int nuevaCantidad) {
        Optional<CarritoItem> itemOpt = carritoRepository.findById(id);
        itemOpt.ifPresent(item -> {
            item.setCantidad(nuevaCantidad);
            carritoRepository.save(item);
        });
    }

    // Elimina un producto del carrito por cliente y producto
    public void eliminarDelCarrito(String clienteId, String productoId) {
        carritoRepository.deleteByClienteIdAndProductoId(clienteId, productoId);
    }

    // Obtiene un ítem del carrito por su ID
    public CarritoItem obtenerPorId(String id) {
        return carritoRepository.findById(id).orElse(null);
    }
}
