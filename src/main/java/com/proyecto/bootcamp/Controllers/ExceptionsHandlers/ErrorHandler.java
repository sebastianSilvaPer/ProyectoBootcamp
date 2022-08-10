package com.proyecto.bootcamp.Controllers.ExceptionsHandlers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.proyecto.bootcamp.Exceptions.JsonResponse;
import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Exceptions.UnAuthorizedException;
import com.proyecto.bootcamp.Exceptions.UniqueValueException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<JsonResponse> handlerInternalServerError(Throwable throwable) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(
                "Internal_Server_Error",
                "There is a problem",
                "We are trying to fix this"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handlerNotfoundException(NotFoundException notFoundException) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(
                "Entity_not_found",
                "the given id doesn     't match any existing record",
                notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<JsonResponse> handlerBadRequest(MethodArgumentTypeMismatchException e, WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse(
                "Format_exception",
                "the given value doesn't match the required type",
                e.getName() + " should be of type " + e.getRequiredType().getSimpleName()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<JsonResponse>> handlerNotValidException(MethodArgumentNotValidException e,
            WebRequest request) {
        List<JsonResponse> errors = e.getBindingResult().getFieldErrors().stream()
                .map(f -> new JsonResponse(
                        "Validation_Exception",
                        "the given value on '" + f.getField() + "' doesn't match the required type",
                        f.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<List<JsonResponse>>(errors,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<JsonResponse>> handlerConstraintViolationException(ConstraintViolationException e,
            WebRequest request) {
        List<JsonResponse> errors = e.getConstraintViolations().stream()
                .map(f -> new JsonResponse(
                        "Paramether_Exception",
                        "the given value '" + f.getInvalidValue() + "' doesn't match the required type",
                        f.getMessage() + ""))
                .collect(Collectors.toList());

        return new ResponseEntity<List<JsonResponse>>(errors,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handlerDataIntegrationException(DataIntegrityViolationException e,
            WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse(
                "Entity_not_found",
                "the given id doesn't match any existing record",
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<JsonResponse> handlerhttpMessageNotReadableException(HttpMessageNotReadableException e,
            WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse("Input_not_readable",
                "The input format it's not valid",
                "The app can not read the values that you are sending"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<JsonResponse> handlerInternalAuthenticationServiceException(InternalAuthenticationServiceException e,
            WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse("Authentication_Exception",
                "The authentication failed",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<JsonResponse> handlerUnAuthorizedException(UnAuthorizedException e, WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse("Authentication_Exception",
                "The authentication failed",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueValueException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<JsonResponse> handlerUniqueValueException(UniqueValueException e, WebRequest request) {

        return new ResponseEntity<JsonResponse>(new JsonResponse("Duplicate_Value_Exception",
                "The value you are trying to insert is already in use",
                e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
