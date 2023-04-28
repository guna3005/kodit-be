package com.kodit.application.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiExceptionResponse> handleCustomException(CustomException exception) {
        ApiExceptionResponse response = exception.getErrorResponse();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
