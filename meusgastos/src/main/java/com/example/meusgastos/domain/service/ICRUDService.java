//quando faz update ta tirando e colocando no banco, create colocando.. td isso precisa entao de request e response
// dto = objeto transferido
package com.example.meusgastos.domain.service;

import java.util.List;

public interface ICRUDService<Request, Response> {
    List<Response> obterTodos();
    Response obterPorId(Long id); //passa id e retorna o usuario
    Response cadastrar (Request dto); //request dto e responde usuario cadastrado
    Response atualizar(Long id, Request dto); 
    void deletar(Long id); //alteracao q "apaga" id do usuario
    
}
