package com.example.appsfinalproject.model;

import java.util.ArrayList;

public class AdministradorGeneral extends Usuario{
    private ArrayList<Local> locales;

    public AdministradorGeneral(ArrayList<Local> locales, String username, String password, String id, Tipo_usuario tipo) {
        super(username,password,id,tipo);
        this.locales = locales;
    }

    public ArrayList<Local> getLocales() {
        return locales;
    }

    public void setLocales(ArrayList<Local> locales) {
        this.locales = locales;
    }
}
