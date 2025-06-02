package com.kathar.app.models;

public class ProductoConStock {
    private Producto producto;
    private int stock;

    public ProductoConStock(Producto producto, int stock) {
        this.producto = producto;
        this.stock = stock;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getStock() {
        return stock;
    }
}
