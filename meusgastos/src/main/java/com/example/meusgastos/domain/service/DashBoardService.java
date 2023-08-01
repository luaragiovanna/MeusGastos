package com.example.meusgastos.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.Enum.ETipoTitulo;
import com.example.meusgastos.domain.dto.dashboard.DashboardResponseDTO;
import com.example.meusgastos.domain.dto.titulos.TituloReponseDTO;

@Service
public class DashBoardService {
     @Autowired
    private TituloService tituloService;

    public DashboardResponseDTO obterFluxoDeCaixa(String periodoInicial, String periodoFinal){
        List<TituloReponseDTO> titulos = tituloService.obterPorDataDeVencimento(periodoInicial, periodoFinal);
        Double totalPagar = 0.0;
        Double totalReceber = 0.0;
        List<TituloReponseDTO> titulosPagar = new ArrayList<>();
        List<TituloReponseDTO> titulosReceber = new ArrayList<>();
        Double saldo = 0.0;
        for(TituloReponseDTO titulo : titulos){
            if(titulo.getTipo() == ETipoTitulo.APAGAR){
                totalPagar += titulo.getValor();
                titulosPagar.add(titulo);
            }else{
                totalReceber += titulo.getValor();
                titulosReceber.add(titulo);
            }
        }
        saldo = totalReceber - totalPagar;
        return new DashboardResponseDTO(totalPagar, totalReceber, saldo, titulosPagar, titulosReceber);
    }
}


