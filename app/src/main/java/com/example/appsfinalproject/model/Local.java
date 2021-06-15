package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Local {
    private String nombreLocal;
    private String nombreEncargado;
    private String direccion;
    private String telefono;
    private Inventario inventario;
    private ContabilidadLocal contabilidad;
    private String id;
    private String photoId;
    private double lat;
    private double lng;

    public Local(){

    }

    public Local(String nombreLocal, String nombreEncargado, String direccion, String telefono, Inventario inventario, String id, String photoID) {
        this.nombreLocal = nombreLocal;
        this.nombreEncargado=nombreEncargado;
        this.telefono = telefono;
        this.inventario = inventario;
        this.id = id;
        this.photoId = photoID;
        this.direccion = direccion;
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

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
