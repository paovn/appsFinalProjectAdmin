package com.example.appsfinalproject.model;

import java.util.Date;

public class RegistroContable {
    private String nombre;
    private Date  fecha;
    private long DateMiliseconds;
    private double Costo;
    private Tipo_registro tipo;
    private String id;

    public RegistroContable(){}

    public RegistroContable(String nombre, Date fecha, double costo, Tipo_registro tipo, String id) {
        this.nombre = nombre;
        this.fecha = fecha;
        DateMiliseconds = fecha.getTime();
        Costo = costo;
        this.tipo = tipo;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return Costo;
    }

    public void setCosto(double costo) {
        Costo = costo;
    }

    public Tipo_registro getTipo() {
        return tipo;
    }

    public void setTipo(Tipo_registro tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDateMiliseconds() {
        return DateMiliseconds;
    }

    public void setDateMiliseconds(long dateMiliseconds) {
        DateMiliseconds = dateMiliseconds;
    }
}
