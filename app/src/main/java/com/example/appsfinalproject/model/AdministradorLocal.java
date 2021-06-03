package com.example.appsfinalproject.model;

public class AdministradorLocal extends Usuario{
    private Local miLocal;

    public AdministradorLocal(Local miLocal, String username, String password, String id, Tipo_usuario tipo) {
        super(username,password,id,tipo);
        this.miLocal = miLocal;
    }

    public Local getMiLocal() {
        return miLocal;
    }

    public void setMiLocal(Local miLocal) {
        this.miLocal = miLocal;
    }
}
