package com.company.musicstorecatalog.Controller;


import com.company.musicstorecatalog.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatus returnStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), returnStatus);
        return new ResponseEntity<ErrorMessage>(errorMessage, returnStatus);
    }
}
