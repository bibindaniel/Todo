package com.studyspring.todocrud.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(AppException.class)
 public ResponseEntity<ErrorResponse> handleAppException(AppException ex){
     ErrorResponse error= new ErrorResponse(ex.getMessage(),ex.getStatus(),LocalDateTime.now());
     return new ResponseEntity<>(error,error.getStatus());

 }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenricException(Exception ex){
        ErrorResponse error= new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR,LocalDateTime.now());
        return new ResponseEntity<>(error,error.getStatus());

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation error",HttpStatus.BAD_REQUEST,LocalDateTime.now());
        errorResponse.setErrors(errors);
     return new ResponseEntity<>(errorResponse,errorResponse.getStatus());
    }
}
