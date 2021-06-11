package com.example.appsfinalproject.model;

import com.google.firebase.firestore.Exclude;

public class Usuario {
    private String username;
    private String idLocal;
    private String id;
    private Tipo_usuario tipo;

    public Usuario(){}

    public Usuario(String username, String id, Tipo_usuario tipo) {
        this.username = username;
        this.id = id;
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tipo_usuario getTipo() {
        return tipo;
    }

    public void setTipo(Tipo_usuario tipo) {
        this.tipo = tipo;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdLocal() {
        return idLocal;
    }
}
