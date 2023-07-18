package com.example.meusgastos.domain.dto.usuario;

public class LoginResponseDTO {
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
    private String token; //gera apos login
    private UsuarioResponseDTO usuario;
    
}
