package com.example.meusgastos.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastos.domain.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {    //pega metodos delete insert e o uso do Long p/ id                     
    Optional<Usuario>  findByEmail(String email); //p restrição
    
}
