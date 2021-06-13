package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class Producto {
    private String nombre;
    private String id;
    private float quantitiy;
    private float lowRange;
    private float middleRange;
    private String presentation;
    private String photId;
    private ArrayList<Registro_producto> registros;

    public Producto(){}

    public Producto(String nombre, String presentation, String id,float lowRange, float middleRange, String photId) {
        this.nombre = nombre;
        this.id = id;
        this.registros = new ArrayList<>();
        this.quantitiy = 0;
        this.lowRange = lowRange;
        this.middleRange = middleRange;
        this.presentation = presentation;
        this.photId = photId;
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

    public float getQuantitiy() {
        return quantitiy;
    }

    public float getLowRange() {
        return lowRange;
    }

    public float getMiddleRange() {
        return middleRange;
    }

    public void setQuantitiy(float quantitiy) {
        this.quantitiy = quantitiy;
    }

    public void setLowRange(float lowRange) {
        this.lowRange = lowRange;
    }

    public void setMiddleRange(float middleRange) {
        this.middleRange = middleRange;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPhotId(String photId) {
        this.photId = photId;
    }

    public String getPhotId() {
        return photId;
    }
}
