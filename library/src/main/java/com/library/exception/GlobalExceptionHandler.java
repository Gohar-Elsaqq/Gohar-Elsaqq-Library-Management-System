package com.library.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.library.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Not Found Record
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFoundRecord(ResourceNotFoundException ex) {

        ErrorResponse errorResponse= new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    //DaplicateRecoredException
    @ExceptionHandler(DaplicateRecoredException.class)
    public ResponseEntity<?> daplicateRecoredException(DaplicateRecoredException ex) {

        ErrorResponse errorResponse= new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}