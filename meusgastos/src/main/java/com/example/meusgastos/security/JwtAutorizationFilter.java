package com.example.meusgastos.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.meusgastos.domain.model.Usuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAutorizationFilter  extends BasicAuthenticationFilter{
    private JwtUtil jwtUtil;
    private UserDetailsSecurityServer userDetailsSecurityServer;
    //usuario faz requisicao mas precisa estar logado

     public JwtAutorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsSecurityServer userDetailsSecurityServer) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsSecurityServer = userDetailsSecurityServer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        
        String header = request.getHeader("Authorization");//passa autorização pelo heder se n tiver bearer ta invalido
        if(header != null && header.startsWith("Bearer ")){
            UsernamePasswordAuthenticationToken auth = getAuthenticationToken(header.substring(7));//partir string, a partir da 7 cada verifica
            if(auth != null && auth.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(auth);//troca contexto agr o usuario pdoe fazer autenticacao
            } 
        }
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
        if(jwtUtil.isValidToken(token)){
            String email = jwtUtil.getUserName(token);
           // UsuarioResponseDTO usuarioResponseDTO = usuarioService.obterPorEmail(email);
           // Usuario usuario = mapper.map(usuarioResponseDTO, Usuario.class);
           Usuario usuario = (Usuario) userDetailsSecurityServer.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }
        return null;
    }



}

