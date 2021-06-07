package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Producto {
    private String nombre;
    private String id;
    private double precio;
    private ArrayList<Registro_producto> registros;

    public Producto(){}

    public Producto(String nombre, String id, double precio) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<Registro_producto> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro_producto> registros) {
        this.registros = registros;
    }
}
