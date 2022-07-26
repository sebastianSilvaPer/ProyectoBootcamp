package com.proyecto.bootcamp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(){

    }   
    
    public UnAuthorizedException(String message){
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause){
        super(message, cause);
    }
}
