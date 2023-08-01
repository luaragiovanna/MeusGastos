package com.example.meusgastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.meusgastos.domain.dto.usuario.UsuarioRequestDTO;
import com.example.meusgastos.domain.dto.usuario.UsuarioResponseDTO;
import com.example.meusgastos.domain.service.UsuarioService;
@CrossOrigin("*")  //p insominia
@RestController //da get push  delete


@RequestMapping("/api/usuarios") //caminho
public class UsuarioController {
    //chama as coisas da service
    @Autowired
    private UsuarioService usuairService;

    //retorna lista de usuario response
    @GetMapping //metodo de get 
    public ResponseEntity<List<UsuarioResponseDTO>>  obterTodos(){ //so pega caminho
        return ResponseEntity.ok(usuairService.obterTodos());
     }

    //via url 
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuairService.obterPorId(id));
    }

    //via json 
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody UsuarioRequestDTO dto){
        UsuarioResponseDTO usuario = usuairService.cadastrar(dto);
        return new ResponseEntity<UsuarioResponseDTO>(usuario, HttpStatus.CREATED); //usuario foi criado
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto){
         UsuarioResponseDTO usuario = usuairService.atualizar(id, dto);
         return new ResponseEntity<UsuarioResponseDTO>(usuario, HttpStatus.OK); //usuario foi criado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        usuairService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
