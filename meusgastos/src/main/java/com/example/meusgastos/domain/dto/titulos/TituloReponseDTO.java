package com.example.meusgastos.domain.dto.titulos;

import java.util.Date;
import java.util.List;

import com.example.meusgastos.domain.Enum.ETipoTitulo;
import com.example.meusgastos.domain.dto.centrodecusto.CentroDeCustoResponseDTO;


public class TituloReponseDTO {
    private Long id;
    private String descricao;
    private ETipoTitulo tipo;
    private List<CentroDeCustoResponseDTO> centrosDeCustos;
    private Double valor;
    private Date dataCadastro;
    private Date dataDeReferencia;
    private Date dataDeVencimento;
    private Date dataDePagamento;
    private String observacao;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public ETipoTitulo getTipo() {
        return tipo;
    }
    public void setTipo(ETipoTitulo tipo) {
        this.tipo = tipo;
    }
    public List<CentroDeCustoResponseDTO> getCentrosDeCustos() {
        return centrosDeCustos;
    }
    public void setCentrosDeCustos(List<CentroDeCustoResponseDTO> centrosDeCustos) {
        this.centrosDeCustos = centrosDeCustos;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Date getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public Date getDataDeReferencia() {
        return dataDeReferencia;
    }
    public void setDataDeReferencia(Date dataDeReferencia) {
        this.dataDeReferencia = dataDeReferencia;
    }
    public Date getDataDeVencimento() {
        return dataDeVencimento;
    }
    public void setDataDeVencimento(Date dataDeVencimento) {
        this.dataDeVencimento = dataDeVencimento;
    }
    public Date getDataDePagamento() {
        return dataDePagamento;
    }
    public void setDataDePagamento(Date dataDePagamento) {
        this.dataDePagamento = dataDePagamento;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
