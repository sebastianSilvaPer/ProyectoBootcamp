package com.proyecto.bootcamp.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.proyecto.bootcamp.Exceptions.JsonResponse;
import com.proyecto.bootcamp.Exceptions.NotFoundException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<JsonResponse> name(Throwable throwable) {
        System.out.println(throwable);
        return null;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handlerNotfoundException(NotFoundException notFoundException) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(
            "Entity_not_found",
            "the given id doesn't match any existing record",
            notFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<JsonResponse> handlerBadRequest(MethodArgumentTypeMismatchException e, WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse(
                "Format_exception",
                "the given value doesn't match the required type",
                e.getName()+" should be of type "+e.getRequiredType().getSimpleName()
            ),
            HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<JsonResponse>> methodNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        List<JsonResponse> errors = e.getBindingResult().getFieldErrors().stream()
        .map(f -> new JsonResponse(
            "Validation_Exception",
            "the given value on '"+f.getField()+"' doesn't match the required type",
            f.getDefaultMessage()
        )).collect(Collectors.toList());
        
        return new ResponseEntity<List<JsonResponse>>(errors,
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<JsonResponse>> constraintViolationException(ConstraintViolationException e, WebRequest request) {
        List<JsonResponse> errors = e.getConstraintViolations().stream()
        .map(f -> new JsonResponse(
            "Paramether_Exception",
            "the given value '"+f.getInvalidValue()+"' doesn't match the required type",
            f.getMessage()+""
        )).collect(Collectors.toList());

        return new ResponseEntity<List<JsonResponse>>(errors,
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> dataIntegrationException(DataIntegrityViolationException e,WebRequest request) {
        
        return new ResponseEntity<JsonResponse>(new JsonResponse(
            "Entity_not_found",
            "the given id doesn't match any existing record",
            e.getMessage()
        ),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<JsonResponse> name(HttpMessageNotReadableException e, WebRequest request) {
        
        return new ResponseEntity<JsonResponse>(new JsonResponse("Input_not_readable",
                                                        "The input format it's not valid",
                                                        "The app can not read the values that you are sending"),HttpStatus.BAD_REQUEST);
    }
}
