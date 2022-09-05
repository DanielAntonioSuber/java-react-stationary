package com.stationary.api.exceptions;

import com.stationary.api.dto.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> anyExceptionHandler(Exception exception, WebRequest webRequest ) {
        var errorDetail = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException exception, WebRequest webRequest ) {
        var errorDetail = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetails> blogExceptionHandler(BlogAppException exception, WebRequest webRequest ) {
        var errorDetail = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, exception.getHttpStatus());
    }


}