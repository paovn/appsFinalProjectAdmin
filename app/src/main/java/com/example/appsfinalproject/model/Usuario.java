package com.example.appsfinalproject.model;

public class Usuario {
    private String username;
    private String password;
    private String idLocal;
    private String id;
    private Tipo_usuario tipo;

    public Usuario(){

    }

    public Usuario(String username, String password, String id, Tipo_usuario tipo) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
