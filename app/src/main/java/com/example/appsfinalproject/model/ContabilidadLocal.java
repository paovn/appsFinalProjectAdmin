package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class ContabilidadLocal {
    private ArrayList<RegistroContable> registros;

    public ContabilidadLocal(ArrayList<RegistroContable> registros) {
        this.registros = registros;
    }

    public ArrayList<RegistroContable> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<RegistroContable> registros) {
        this.registros = registros;
    }
}
