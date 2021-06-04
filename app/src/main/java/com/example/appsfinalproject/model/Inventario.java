package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Inventario {
    private int elementos;
    private ArrayList<Producto> productos_inventario;

    public Inventario(int elementos, ArrayList<Producto> productos_inventario) {
        this.elementos = elementos;
        this.productos_inventario = productos_inventario;
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
}
