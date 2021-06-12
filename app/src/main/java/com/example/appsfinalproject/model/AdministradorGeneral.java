package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class AdministradorGeneral extends Usuario{
    private ArrayList<String> idlocales;

    public AdministradorGeneral() {
        idlocales = new ArrayList<>();
    }

    public AdministradorGeneral(String username, String id, Tipo_usuario tipo) {
        super(username,id,tipo);
        this.idlocales = new ArrayList<>();
    }

    public ArrayList<String> getIdLocales() {
        return idlocales;
    }

    public void setIdLocales(ArrayList<String> idlocales) {
        this.idlocales = idlocales;
    }
}
