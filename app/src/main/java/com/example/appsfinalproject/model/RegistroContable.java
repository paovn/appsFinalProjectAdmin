package com.example.appsfinalproject.model;

import java.util.Date;

public class RegistroContable {
    private String RegistroContable;
    private Date  fecha;
    private double Costo;
    private Tipo_registro tipo;

    public RegistroContable(String registroContable, Date fecha, double costo, Tipo_registro tipo) {
        RegistroContable = registroContable;
        this.fecha = fecha;
        Costo = costo;
        this.tipo = tipo;
    }

    public String getRegistroContable() {
        return RegistroContable;
    }

    public void setRegistroContable(String registroContable) {
        RegistroContable = registroContable;
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
}
