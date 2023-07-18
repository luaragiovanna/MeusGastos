package com.example.meusgastos.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
//classe transforma erros em algo que possamos escrever
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.meusgastos.common.ConversorData;
import com.example.meusgastos.domain.exception.ResourceBadRequestException;
import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.ErroResposta;
@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class) //NOT FOUND
    public ResponseEntity<ErroResposta> handlerResourceNotFoundException(ResourceNotFoundException ex){
        String dataHora = ConversorData.converterDataParaDataHora(new Date()); //se gerar exceção, ele vi disparar exceções q gera new date

        ErroResposta erro = new ErroResposta(dataHora, HttpStatus.NOT_FOUND.value(),"NOT FOUND", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ResourceBadRequestException.class) //BAD REQUEST
    public ResponseEntity<ErroResposta> handlerResourceBadReuest(ResourceBadRequestException ex){
        String dataHora = ConversorData.converterDataParaDataHora(new Date()); 
        
        ErroResposta erro = new ErroResposta(dataHora, HttpStatus.BAD_REQUEST.value(),"BAD REQUEST", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class) //INTERNAL SERVER ERROR
    public ResponseEntity<ErroResposta> handlerRequestException(Exception ex){
        String dataHora = ConversorData.converterDataParaDataHora(new Date()); 
        
        ErroResposta erro = new ErroResposta(dataHora, HttpStatus.INTERNAL_SERVER_ERROR.value(),"INTERNAL SERVER ERROR", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    
}
    

