package com.example.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.dto.titulos.TituloReponseDTO;
import com.example.meusgastos.domain.dto.titulos.TituloRequestDTO;
import com.example.meusgastos.domain.exception.ResourceBadRequestException;
import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.Titulo;
import com.example.meusgastos.domain.model.Usuario;
import com.example.meusgastos.domain.repository.TituloRepository;

@Service
public class TituloService implements ICRUDService<TituloRequestDTO, TituloReponseDTO> {

    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private ModelMapper mapper;

    public List<TituloReponseDTO> obterTodos() {
        // pega o usuario q está logado
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Titulo> titulos  = tituloRepository.findByUsuario(usuario);
        return titulos.stream().map(titulo -> mapper.map(titulo, TituloReponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TituloReponseDTO obterPorId(Long id) {
        Optional<Titulo> optTitulo = tituloRepository.findById(id);
        if (optTitulo.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel encontrar");
        } else {
            return mapper.map(optTitulo.get(), TituloReponseDTO.class);
        }
    }

    @Override
    public TituloReponseDTO cadastrar(TituloRequestDTO dto) {

        validarTitulo(dto);
        Titulo titulo = mapper.map(dto, Titulo.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(null);
        titulo.setDataCadastro(new Date());
        titulo = tituloRepository.save(titulo);
        return mapper.map(titulo, TituloReponseDTO.class);
    }

    @Override
    public TituloReponseDTO atualizar(Long id, TituloRequestDTO dto) {
        obterPorId(id);
        validarTitulo(dto);
        Titulo titulo = mapper.map(dto, Titulo.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(id);
        titulo = tituloRepository.save(titulo);
        return mapper.map(titulo, TituloReponseDTO.class);

    }

    @Override
    public void deletar(Long id) {

       obterPorId(id);
       tituloRepository.deleteById(id);
    }

    private void validarTitulo(TituloRequestDTO dto) {
            if(dto.getTipo() == null || dto.getDataDeVencimento() == null || dto.getValor() == null || dto.getDescricao() == null){
            throw new ResourceBadRequestException("Titulo inválido. Campos obrogatorios");
        }
    }
    //transforma em tituloresponseDTO

    public List<TituloReponseDTO> obterPorDataDeVencimento(String periodoInicial, String periodoFinal){
        List<Titulo> titulos = tituloRepository.obterFluxoCaixaPorDataVencimento(periodoInicial, periodoFinal);
        return titulos.stream()
        .map(titulo -> mapper.map(titulo, TituloReponseDTO.class))
        .collect(Collectors.toList());
    }

}
