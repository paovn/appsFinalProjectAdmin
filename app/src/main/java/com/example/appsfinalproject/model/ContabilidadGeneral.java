package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class ContabilidadGeneral {

    private ArrayList<ContabilidadLocal> contabilidades;

    public ContabilidadGeneral(ArrayList<ContabilidadLocal> contabilidades) {
        this.contabilidades = contabilidades;
    }

    public ArrayList<ContabilidadLocal> getContabilidades() {
        return contabilidades;
    }

    public void setContabilidades(ArrayList<ContabilidadLocal> contabilidades) {
        this.contabilidades = contabilidades;
    }
}
