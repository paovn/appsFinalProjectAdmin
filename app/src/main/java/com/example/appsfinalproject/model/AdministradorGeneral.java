package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class AdministradorGeneral extends Usuario{
    private ArrayList<String> idlocales;

    public AdministradorGeneral() {
    }

    public AdministradorGeneral(String username, String password, String id, Tipo_usuario tipo) {
        super(username,password,id,tipo);
        this.idlocales = new ArrayList<>();
    }

    public ArrayList<String> getIdLocales() {
        return idlocales;
    }

    public void setIdLocales(ArrayList<String> idlocales) {
        this.idlocales = idlocales;
    }
}
