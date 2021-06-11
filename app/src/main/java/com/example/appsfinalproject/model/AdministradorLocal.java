package com.example.appsfinalproject.model;

public class AdministradorLocal extends Usuario{
    private String idLocal;

    public AdministradorLocal(){

    }

    public AdministradorLocal(String idLocal, String username, String id, Tipo_usuario tipo) {
        super(username,id,tipo);
        this.idLocal = idLocal;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }
}
