package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Inventario {
    private int elementos;
    private ArrayList<Producto> productos_inventario;

    public Inventario() {
        this.elementos = 0;
        this.productos_inventario = new ArrayList<>();
    }

    public int getElementos() {
        return elementos;
    }

    public void setElementos(int elementos) {
        this.elementos = elementos;
    }

    public ArrayList<Producto> getProductos_inventario() {
        return productos_inventario;
    }

    public void setProductos_inventario(ArrayList<Producto> productos_inventario) {
        this.productos_inventario = productos_inventario;
    }

    public void addProducto(String nombre, String id, double precio) {
        Producto producto = new Producto(nombre,id,precio);
        productos_inventario.add(producto);
    }
}
