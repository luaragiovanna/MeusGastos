package com.example.meusgastos.security;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.meusgastos.domain.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    //dados do usuario salvado dentro do token
    @Value("${auth.jwt.secret}")
    private String jwtSecret;
    @Value("${auth.jwt-expiration-milliseg}")
    private Long jwtExpirationMilliseg;



    public String gerarToken(Authentication authenticateAction){
        //usuario loga e gera nova data + os milisegundos
        Date dataExpiracao = new Date(new Date().getTime() + jwtExpirationMilliseg);
        Usuario usuario = (Usuario) authenticateAction.getPrincipal(); //pega quem se logou e diz q ele sempre vai ser um usuario
        //gerar token
        try {
            Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));    
            
            return Jwts.builder().setSubject(usuario.getUsername()).setIssuedAt(new Date()).setExpiration(dataExpiracao).signWith(secretKey).compact();
              
         
        } catch (Exception e) { //se algum dado falhar
            System.out.println(e.getMessage());
            return " ";
        }

    };


    private Claims getClaims(String token){ //desmenbra token
        //usa chave pra quebrar o token
        try {
             Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8")); 
             Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
             return claims;
        } catch (Exception e) {
           System.out.println(e.getMessage());
            return null;
        }
    };


    public String getUserName(String token){
        Claims claims = getClaims(token); //ja recebe token quebrado
        if(claims == null){
            return null;
        }
        return claims.getSubject(); //pra dar get username
    };

    public boolean isValidToken(String token){//pega token
          Claims claims = getClaims(token); //ja recebe token quebrado
        if(claims == null){
            return false;
        }
        String email = claims.getSubject();
        Date dataExpiracao = claims.getExpiration();
        Date agora = new Date(System.currentTimeMillis());

        if((email != null) && (agora.before(dataExpiracao))){
            return true; //usuario valido 

        }
        return false;
    };
    };
    



