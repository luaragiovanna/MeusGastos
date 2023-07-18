package com.example.meusgastos.domain.Enum;

public enum ETipoTitulo {
    ARECEBER("A Receber"), //cri um titulo q tem 2 opções (receber ou pagar)
    APAGAR("A Pagar");
    private String valor;

    private ETipoTitulo(String valor){
        this.valor = valor;
    } 
    public String getValor() {
        return valor;
    }
    
}
