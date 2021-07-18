package com.demo.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected void handleNotRequestBody(HttpMessageNotReadableException ex) {
        logger.error("::: {} - {}", ex.getClass(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleArgumentException(MethodArgumentNotValidException ex){
        logger.error("::: {} - {}", ex.getClass(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("field "+ex.getBindingResult().getFieldError().getField()+" - "+ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected void handleGeneralException(Exception ex) {
        logger.error("::: {} - {}", ex.getClass(), ex.getMessage());
    }


}
