package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Local {
    private String nombreLocal;
    private String nombreEncargado;
    private String telefono;
    private Inventario inventario;
    private ContabilidadLocal contabilidad;
    private String id;

    public Local(){

    }

    public Local(String nombreLocal, String nombreEncargado,String telefono, Inventario inventario, String id) {
        this.nombreLocal = nombreLocal;
        this.nombreEncargado=nombreEncargado;
        this.telefono = telefono;
        this.inventario = inventario;
        this.id = id;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombre) {
        this.nombreLocal = nombre;
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

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
