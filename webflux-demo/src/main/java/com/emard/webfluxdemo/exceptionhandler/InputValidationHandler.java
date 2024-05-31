package com.emard.webfluxdemo.exceptionhandler;

import com.emard.webfluxdemo.dto.InputFailedValidationResponse;
import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException e){
        InputFailedValidationResponse response = new InputFailedValidationResponse();
        response.setErrorCode(e.ERROR_CODE);
        response.setInput(e.getInput());
        response.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
