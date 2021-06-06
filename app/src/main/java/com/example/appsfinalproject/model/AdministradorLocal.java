package com.example.appsfinalproject.model;

public class AdministradorLocal extends Usuario{
    private String idLocal;

    public AdministradorLocal(String idLocal, String username, String password, String id, Tipo_usuario tipo) {
        super(username,password,id,tipo);
        this.idLocal = idLocal;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }
}
