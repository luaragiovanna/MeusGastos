package com.example.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.dto.usuario.UsuarioRequestDTO;
import com.example.meusgastos.domain.dto.usuario.UsuarioResponseDTO;
import com.example.meusgastos.domain.exception.ResourceBadRequestException;
import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.Usuario;
import com.example.meusgastos.domain.repository.UsuarioRepository;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDTO, UsuarioResponseDTO>{
    @Autowired //var sem instanciar
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll(); //pega atributos iguais e transforma um no outro
        return usuarios.stream().map(usuario->mapper.map(usuario, UsuarioResponseDTO.class)).collect(Collectors.toList());
        // ^ percorrer usuario, pega usuario da lista e da um map,
        //pega parametro comum e trnsforma usuario em list de usuarioresponseDTO so cm parametros em comum
    }

    @Override
    public UsuarioResponseDTO obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id); //opção se retorna vai instanciar, caso n, n vai dar nd
        if(optUsuario.isEmpty()){
           throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }
        return  mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        //hibernate trabalha cm request e response
        //usuario response retorna td que o request envia
        //mapper comparar tributo a atributo, dos dois objetos de duas classes, atributos iguais ele passa um atributo p/ outro
        // tem user request enviar a model (mas n tem msm cmpos), mapper pega campos iguis e resto vai nulo
        //transformar usarioresquest em usuario da model, salvar pegar usuario q foi salvo e transformar
        //-----------------------EXCEÇÕES--------------------------------------------------------------------//
        //dto n pode vir sem email e sem senha
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new ResourceBadRequestException("Email e senha são obrigatórios");
        }
        //nao pode cadastrar com email ja existente       //buscando usuario por email e pegando esse email
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
        if(optUsuario.isPresent()){
            throw new ResourceBadRequestException("Já existe um usuário cadastrado com esse email: " + dto.getEmail());
        }
        //------------------------------------------------------------------------------------------------------//
        Usuario usuario = mapper.map(dto, Usuario.class); //usuario transformado em usuario da model
        usuario.setDataCadastro(new Date());
        //------encriptar senha--------//
        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
        usuario.setId(null);//id sempre nulo qm gera he o banco

        usuario = usuarioRepository.save(usuario); //salvou usuario
        //retornar response mas transformar
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioBanco = obterPorId(id);
        
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new ResourceBadRequestException("Email e senha são obrigatórios");
        }
        //transformar usuariorequest em usuario da model
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setId(id); //cria novo usuario mas com id diferente
        usuario.setDataCadastro(usuarioBanco.getDataCadastro());
        usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        //usuario.setDataCadastro(dto)
        
        usuario = usuarioRepository.save(usuario); //salva
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        //APAGAR USUARIO
        /*obterPorId(id);//usuario tem q existir pra apagar
        usuarioRepository.deleteById(id); //apaga linha do usuario da tabela*/

        //INATIVAR USUARIO
         Optional<Usuario> optUsuario = usuarioRepository.findById(id); //opção se retorna vai instanciar, caso n, n vai dar nd
        if(optUsuario.isEmpty()){
           throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }
        Usuario usuario = optUsuario.get(); //pegando o usuario
        usuario.setDataInativacao(new Date()); //criando uma data de inativacao
        usuarioRepository.save(usuario); //salvando o usuario com a nova data

    }
    
}
