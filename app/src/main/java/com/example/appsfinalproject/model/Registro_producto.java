package com.example.appsfinalproject.model;

import java.util.Date;

public class Registro_producto {
    private String id;
    private Date fecha;
    private float cantidad;
    private float precio;
    private float total;


    public Registro_producto() {
    }



    public Registro_producto(String id, Date fecha, float cantidad, float precio) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = cantidad*precio;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPrecio() {
        return precio;
    }

    public float getTotal() {
        return total;
    }
    public float getCantidad() {
        return cantidad;
    }

}
