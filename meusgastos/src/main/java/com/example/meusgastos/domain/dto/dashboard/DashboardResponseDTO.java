package com.example.meusgastos.domain.dto.dashboard;

import java.util.List;

import com.example.meusgastos.domain.dto.titulos.TituloReponseDTO;

public class DashboardResponseDTO {
    private Double totalPagar;
    private Double totalReceber;
    private Double saldo;
    private List<TituloReponseDTO> titulosPagar;
    private List<TituloReponseDTO> titulosReceber;

    public DashboardResponseDTO(){}
    //construtor pq n tem mapper
    //n faz operacao no banco
    
    
    public DashboardResponseDTO(Double totalPagar, Double totalReceber, Double saldo, List<TituloReponseDTO> titulosPagar, List<TituloReponseDTO> titulosReceber) {
        this.totalPagar = totalPagar;
        this.totalReceber = totalReceber;
        this.saldo = saldo;
        this.titulosPagar = titulosPagar;
        this.titulosReceber = titulosReceber;
    }

    public Double getTotalPagar() {
        return totalPagar;
    }
    public void setTotalPagar(Double totalPagar) {
        this.totalPagar = totalPagar;
    }
    public Double getTotalReceber() {
        return totalReceber;
    }
    public void setTotalReceber(Double totalReceber) {
        this.totalReceber = totalReceber;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    public List<TituloReponseDTO> getTitulosPagar() {
        return titulosPagar;
    }
    public void setTitulosPagar(List<TituloReponseDTO> titulosPagar) {
        this.titulosPagar = titulosPagar;
    }
    public List<TituloReponseDTO> getTitulosReceber() {
        return titulosReceber;
    }
    public void setTitulosReceber(List<TituloReponseDTO> titulosReceber) {
        this.titulosReceber = titulosReceber;
    }

    
}


