package com.example.appsfinalproject.model;

import java.util.ArrayList;
import java.util.Date;

public class ContabilidadLocal {
    private ArrayList<RegistroContable> registros;
    private String id;

    public ContabilidadLocal() {
    }

    public ContabilidadLocal(String id) {
        this.registros = new ArrayList<>();
        this.id = id;
    }

    public ArrayList<RegistroContable> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<RegistroContable> registros) {
        this.registros = registros;
    }

    public void addRegistro(String nombre, Date fecha, double costo, Tipo_registro tipo, String id) {
        RegistroContable reg = new RegistroContable(nombre,fecha,costo,tipo,id);
        registros.add(reg);
    }

    public String getId() {
        return id;
    }

}
