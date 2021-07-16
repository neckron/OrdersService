package com.demo.service.exception;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

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
