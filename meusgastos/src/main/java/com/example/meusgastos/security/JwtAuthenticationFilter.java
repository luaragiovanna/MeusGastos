package com.example.meusgastos.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.meusgastos.common.ConversorData;
import com.example.meusgastos.domain.dto.usuario.LoginRequestDTO;
import com.example.meusgastos.domain.dto.usuario.LoginResponseDTO;
import com.example.meusgastos.domain.dto.usuario.UsuarioResponseDTO;
import com.example.meusgastos.domain.model.ErroResposta;
import com.example.meusgastos.domain.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

//import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //abertura de seção                     //classe do spring
//criptografar senha

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        super();

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth");
        }
    
        //metodo
      @Override
      public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        //qm faz a autenticacao
        //primeiro try mapper transforma em login o q vem na request
        //cria token da utenticacao email e senha
        //manda autenticar token
        //se n dar certo(1= usuario senha invalido; 2= erro que nao pode ser tratado)
       try{
            LoginRequestDTO login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        }catch(BadCredentialsException e){//nao compativeis cm credenciais
            throw new BadCredentialsException("Usuário ou senha Invalidos");
        }catch(Exception e){//nao tem como tratar erro
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }
////////////////////////////AUTENTICAÇÃO DEU CERTO
    //filterchain chain

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{
        Usuario usuario = (Usuario) authResult.getPrincipal(); //pega usuario e gera
        String token = jwtUtil.gerarToken(authResult);
       // Alteração - UsuarioResponseDTO usuarioResponse =  mapper.map(usuario, UsuarioResponseDTO.class);
        UsuarioResponseDTO usuarioResponse = new UsuarioResponseDTO(); //guarda usuaraio e tonken
        //mapeando usuario
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setFoto(usuario.getFoto());
        usuarioResponse.setDataCadastro(usuario.getDataCadastro());
        usuarioResponse.setDataInativacao(usuario.getDataInativacao());
        //mapear token
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken("Bearer " + token);
        loginResponseDTO.setUsuario(usuarioResponse);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        //p/ retornar o tipo de caracterização ^^^
        response.getWriter().write(new Gson().toJson(loginResponseDTO));//transforma dados p json
    }

    ////////////////////////////////NAO DEU CERTO A AUTENTICAÇÃO
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException{
        String dataHora = ConversorData.converterDataParaDataHora(new Date());
        //passa data e hora
        ErroResposta resposta =  new ErroResposta(dataHora, 401, "Unauthorized", failed.getMessage());
        //erro 401 comum e nao autorizado e getMessage pra criar msg de erro
        //status do erro
        response.setStatus(401);
        //nao bugar msg de erro
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(resposta)); //converter p/ json erro
    }
}
