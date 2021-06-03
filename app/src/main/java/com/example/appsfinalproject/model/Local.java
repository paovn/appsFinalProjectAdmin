package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Local {
    private String nombre;
    private String telefono;
    private Inventario inventario;
    private ContabilidadLocal contabilidad;

    public Local(String nombre, String telefono, Inventario inventario) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.inventario = inventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public ContabilidadLocal getContabilidad() {
        return contabilidad;
    }

    public void setContabilidad(ContabilidadLocal contabilidad) {
        this.contabilidad = contabilidad;
    }
}
