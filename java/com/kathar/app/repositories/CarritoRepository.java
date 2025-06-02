package com.kathar.app.repositories;

import com.kathar.app.models.CarritoItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarritoRepository extends MongoRepository<CarritoItem, String> {
    List<CarritoItem> findByClienteId(String clienteId);
    CarritoItem findByClienteIdAndProductoId(String clienteId, String productoId);
    void deleteByClienteIdAndProductoId(String clienteId, String productoId);
}
