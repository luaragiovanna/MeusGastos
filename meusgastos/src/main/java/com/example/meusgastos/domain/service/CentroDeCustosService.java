package com.example.meusgastos.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.dto.centrodecusto.CentroDeCustoRequestDTO;
import com.example.meusgastos.domain.dto.centrodecusto.CentroDeCustoResponseDTO;
import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.CentroDeCusto;
import com.example.meusgastos.domain.model.Usuario;
import com.example.meusgastos.domain.repository.CentroDeCustoRepository;

@Service
public class CentroDeCustosService implements ICRUDService<CentroDeCustoRequestDTO, CentroDeCustoResponseDTO> {

    @Autowired
    private CentroDeCustoRepository centroDeCustoRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CentroDeCustoResponseDTO> obterTodos() {
       Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       List<CentroDeCusto> lista = centroDeCustoRepository.findByUsuario(usuario);
       //transforma o centro de custo em centro de custo DTO
       return lista.stream().map(centroDeCusto->mapper.map(centroDeCusto,CentroDeCustoResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CentroDeCustoResponseDTO obterPorId(Long id){
        Optional<CentroDeCusto> optCentrodeCusto = centroDeCustoRepository.findById(id);
       if(optCentrodeCusto.isEmpty()){
        throw new ResourceNotFoundException("Não foi possivel encontrar o centro de custo com o id" + id);
       }
       return mapper.map(optCentrodeCusto.get(),CentroDeCustoResponseDTO.class);
       
    }

    @Override
    public CentroDeCustoResponseDTO cadastrar(CentroDeCustoRequestDTO dto) {

      CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
      Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      centroDeCusto.setUsuario(usuario);
      centroDeCusto.setId(null);
      centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
      return mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class);
    }

    @Override
    public CentroDeCustoResponseDTO atualizar(Long id, CentroDeCustoRequestDTO dto) {
    obterPorId(id);
    CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
      Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      centroDeCusto.setUsuario(usuario);
      centroDeCusto.setId(id);
      centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
      return mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class);
    
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        centroDeCustoRepository.deleteById(id);
    }

}
