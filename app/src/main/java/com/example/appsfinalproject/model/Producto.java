package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Producto {
    private String nombre;
    private String id;
    private ArrayList<Registro_producto> registros;

    public Producto(){}

    public Producto(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.registros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Registro_producto> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro_producto> registros) {
        this.registros = registros;
    }

    public int getQuantity() {
        int q = 0;
        for(Registro_producto r : registros) {
            q += r.getCantidad();
        }
        return q;
    }
}
