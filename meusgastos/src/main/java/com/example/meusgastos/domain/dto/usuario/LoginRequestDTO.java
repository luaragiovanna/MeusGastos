package com.example.meusgastos.domain.dto.usuario;

public class LoginRequestDTO { //quado for logar
    private String  email;
    private String  senha;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
 
}
