package com.proyecto.bootcamp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UniqueValueException extends RuntimeException{
    
    public UniqueValueException(){

    }   
    
    public UniqueValueException(String message){
        super(message);
    }

    public UniqueValueException(String message, Throwable cause){
        super(message, cause);
    }
}
