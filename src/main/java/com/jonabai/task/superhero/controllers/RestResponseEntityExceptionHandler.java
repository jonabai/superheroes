package com.jonabai.task.superhero.controllers;

import com.jonabai.task.superhero.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom ResponseEntity Exception Handler
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){

        return new ResponseEntity<>("Resource Not Found. " + exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception exception, WebRequest request){

        return new ResponseEntity<>("Invalid argument. " + exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

}
